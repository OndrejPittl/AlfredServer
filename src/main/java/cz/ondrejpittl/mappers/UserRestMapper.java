package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;
import cz.ondrejpittl.utils.StringUtils;

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
        dto.setBirth(user.getBirth());
        dto.setToken(user.getToken());


        List<User> userFriends = user.getFriends();
        List<User> userInFREQs = user.getIncomingFriendRequests();
        List<User> userOutFREQs = user.getOutcomingFriendRequests();

        if(userFriends != null) {
            List<UserDTO> friends = new ArrayList<>();
            for(User u : userFriends)
                friends.add(this.toBasicDTO(u));
            dto.setFriends(friends);
        }

        if(userInFREQs != null) {
            List<UserDTO> reqs = new ArrayList<>();
            for(User u : userInFREQs)
                reqs.add(this.toBasicDTO(u));
            dto.setInFReqs(reqs);
        }

        if(userOutFREQs != null) {
            List<UserDTO> reqs = new ArrayList<>();
            for(User u : userOutFREQs)
                reqs.add(this.toBasicDTO(u));
            dto.setOutFReqs(reqs);
        }


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

    private UserDTO toBasicDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setSlug(user.getSlug());
        dto.setBirth(user.getBirth());
        return dto;
    }



    public User fromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(StringUtils.stripTags(dto.getFirstName()));
        user.setLastName(StringUtils.stripTags(dto.getLastName()));
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhoto(dto.getPhoto());
        user.setSlug(dto.getSlug());
        user.setBirth(dto.getBirth());
        user.setPassword(dto.getPassword());
        user.setConfirmPassword(dto.getConfirmPassword());

        return user;
    }
}
