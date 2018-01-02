package cz.ondrejpittl.mappers;

import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static com.sun.tools.internal.xjc.reader.Ring.add;

@ApplicationScoped
public class TagRestMapper {

    @Inject
    TagService service;

    @Inject
    TagRepository tagRepository;

    @Inject
    PostRestMapper postMapper;

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
        return dto;
    }

    public Tag fromDTO(TagDTO dto) {
        return service.getOrCreateTag(dto.getName());
    }
}
