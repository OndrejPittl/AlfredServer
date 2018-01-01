package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostRestMapper {

    @Inject
    UserRepository userRepository;

    public List<PostDTO> toDTOs(List<Post> posts) {
        List<PostDTO> dtos = new ArrayList<>();

        for (Post post : posts) {
            dtos.add(this.toDTO(post));
        }

        return dtos;
    }

    public PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setUserId(post.getUser().getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setImage(post.getImage());
        dto.setDate(post.getDate());
        dto.setTags(post.getTags());
        return dto;
    }

    public Post fromDTO(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setUser(userRepository.findBy(dto.getUserId())); //dto.getUserId()
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setImage(dto.getImage());
        post.setDate(dto.getDate());
        post.setTags(dto.getTags());
        return post;
    }
}
