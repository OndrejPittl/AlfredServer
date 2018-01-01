package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TagRestMapper {

    @Inject
    TagRepository tagRepository;

    public List<TagDTO> toDTOs(List<Tag> tags) {
        List<TagDTO> dtos = new ArrayList<>();

        for (Tag tag : tags) {
            dtos.add(this.toDTO(tag));
        }

        return dtos;
    }

    public TagDTO toDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setPosts(tag.getPosts());
        return dto;
    }

    public Tag fromDTO(TagDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        tag.setPosts(dto.getPosts());
        return tag;
    }
}
