package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.TagRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class TagServiceImpl implements TagService {

    @Inject
    TagRepository tagRepository;

    @Inject
    TagRestMapper tagMapper;

    public List<Tag> getAllTags() {
        return this.tagRepository.findAll();
    }

    public Tag getTag(Long id) {
        return this.tagRepository.findBy(id);
    }


    @Transactional
    public Tag createTag(TagDTO tag) {
        return tagRepository.save(tagMapper.fromDTO(tag));
    }

    public Tag getOrCreateTag(String tag) {
        Tag t = this.tagRepository.findFirst1ByNameLike(tag);

        if(t == null) {
            Dev.print("Tag " + tag + " not found. Creating a new one.");
            t = tagRepository.save(new Tag(tag));
            //t = new Tag(tag);
        }

        return t;
    }

    public int removeOrphans(Set<Tag> tags) {
        Dev.print("POST DELETE: Checking Tag orphans.");

        int removedCount = 0;

        Iterator<Tag> it = tags.iterator();
        while(it.hasNext()){

            Tag t = this.tagRepository.findBy(it.next().getId());
            boolean isOrphan = t.getPosts().size() <= 0;

            Dev.print("checking " + t.getName());

            // not an orphan –> skip
            if(!isOrphan) continue;

            Dev.print("POST DELETE: Tag ID " + t.getId() + "(" + t.getName() + ") is orphan. Removing.");
            this.tagRepository.removeById(t.getId());
            removedCount++;
        }

        return removedCount;
    }

}
