package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import java.util.List;
import java.util.Set;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTag(Long id);

    Tag createTag(TagDTO tag);

    Tag getOrCreateTag(String tag);

    int removeOrphans(Set<Tag> tags);

    int removeOrphans();
}
