package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class UserRestMapper {

    @Inject
    PostRestMapper postMapper;

    public List<UserDTO> toDTOs(List<User> users) {
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(this.toDTO(user));
        }

        return dtos;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setSex(user.getSex());
        dto.setPhoto(user.getPhoto());
        dto.setSlug(user.getSlug());
        dto.setPassword(user.getPassword());

        if (user.getPosts() != null) {
            dto.setPosts(new HashSet<PostDTO>(){{
                for (Post post : user.getPosts()) {
                    add(postMapper.toDTO(post));
                }
            }});
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
        return user;
    }
}
