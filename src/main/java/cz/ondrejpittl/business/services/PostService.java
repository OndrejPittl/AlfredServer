package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.rest.dtos.PostDTO;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(Long id);

    Post createPost(PostDTO dto);

    Post removePost(Long id);

    Post modifyPost(Long id, PostDTO dto);
}
