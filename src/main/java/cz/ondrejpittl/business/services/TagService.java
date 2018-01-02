package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import java.util.List;

public interface TagService {

    public List<Tag> getAllTags();

    public Tag getTag(Long id);

    public Tag createTag(TagDTO tag);

    public Tag getOrCreateTag(String tag);
}
