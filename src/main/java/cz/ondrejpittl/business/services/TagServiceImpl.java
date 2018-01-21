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
import java.util.HashSet;
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

    public Tag getTag(String tag) {
        return this.tagRepository.findByNameLikeOrderByNameAsc(tag);
    }

    @Transactional
    public Tag createTag(TagDTO tag) {
        return tagRepository.save(tagMapper.fromDTO(tag));
    }

    public Tag getOrCreateTag(String tag) {
        Tag t = this.tagRepository.findByNameLikeOrderByNameAsc(tag);

        if(t == null) {
            t = tagRepository.save(new Tag(tag));
        }

        return t;
    }

    public int removeOrphans(Set<Tag> tags) {
        int removedCount = 0;

        Iterator<Tag> it = tags.iterator();
        while(it.hasNext()){

            Tag t = this.tagRepository.findBy(it.next().getId());
            boolean isOrphan = t.getPosts().size() <= 0;

            // not an orphan –> skip
            if(!isOrphan) continue;

            this.tagRepository.removeById(t.getId());
            removedCount++;
        }

        this.tagRepository.flush();
        return removedCount;
    }

    public int removeOrphans() {
        List<Tag> tags = this.tagRepository.findAll();

        if(tags == null) {
            return 0;
        }

        return this.removeOrphans(new HashSet<>(tags));
    }

}
