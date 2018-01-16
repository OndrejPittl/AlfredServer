package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class UserRestMapper {

    @Inject
    PostRestMapper postMapper;


    public List<UserDTO> toDTOs(List<User> users) {
        return this.toDTOs(users, true);
    }

    public List<UserDTO> toDTOs(List<User> users, boolean deep) {
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(this.toDTO(user, deep));
        }

        return dtos;
    }

    public UserDTO toDTO(User user) {
        return this.toDTO(user, true);
    }

    public UserDTO toDTO(User user, boolean deep) {

        if(user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setSex(user.getSex());
        dto.setPhoto(user.getPhoto());
        dto.setSlug(user.getSlug());
        //dto.setPassword(user.getPassword());
        dto.setToken(user.getToken());

        if(!deep) return dto;

        if (user.getPosts() != null) {
            Set<PostDTO> posts = new LinkedHashSet<>();
            for (Post post : user.getPosts()) {
                posts.add(postMapper.toDTO(post));
            }
            dto.setPosts(posts);
        }

        return dto;
    }



    public User fromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhoto(dto.getPhoto());
        user.setSlug(dto.getSlug());
        user.setPassword(dto.getPassword());
        user.setConfirmPassword(dto.getConfirmPassword());

        return user;
    }
}
