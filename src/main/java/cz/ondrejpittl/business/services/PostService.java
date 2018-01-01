package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.PostDTO;

import java.util.List;

public interface PostService {

    public List<Post> getAllPosts();

    public Post getPost(Long id);

    public Post createPost(PostDTO post);
}
