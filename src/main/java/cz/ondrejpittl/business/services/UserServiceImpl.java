package cz.ondrejpittl.business.services;

import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserRestMapper userMapper;



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findBy(id);
    }


    @Transactional
    public User createUser(UserDTO user) {
        User u = userMapper.fromDTO(user);

        u.setPosts(new LinkedList<Post>(){{
            add(new Post("post1_title", "post1_body", "post1_img-path", new Date(), u, new LinkedList<Tag>(){{
                add(new Tag("prvniTag"));
                add(new Tag("druhyTag"));
            }}));
            add(new Post("post2_title", "post2_body", "post2_img-path", new Date(), u, new LinkedList<Tag>(){{
                add(new Tag("druhyTag"));
                add(new Tag("tretiTag"));
            }}));
        }});

        return userRepository.save(u);
    }
}
