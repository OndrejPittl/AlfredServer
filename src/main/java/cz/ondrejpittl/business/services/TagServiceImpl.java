package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.mappers.TagRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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

}
