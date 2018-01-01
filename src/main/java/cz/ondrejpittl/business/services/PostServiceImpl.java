package cz.ondrejpittl.business.services;

import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    @Inject
    PostRepository postRepository;

    @Inject
    PostRestMapper postMapper;

    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    public Post getPost(Long id) {
        return this.postRepository.findBy(id);
    }

    @Transactional
    public Post createPost(PostDTO post) {
        return postRepository.save(postMapper.fromDTO(post));
    }
}
