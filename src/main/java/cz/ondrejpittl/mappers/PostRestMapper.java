package cz.ondrejpittl.mappers;

import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class PostRestMapper {

    @Inject
    UserRepository userRepository;

    @Inject
    TagRestMapper tagMapper;

    @Inject
    CommentRestMapper commentMapper;

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

        if (post.getTags() != null) {
            dto.setTags(new HashSet<TagDTO>(){{
                for (Tag tag : post.getTags()) {
                    add(tagMapper.toDTO(tag));
                }
            }});
        }

        if (post.getComments() != null) {
            dto.setComments(new HashSet<CommentDTO>(){{
                for (Comment comment : post.getComments()) {
                    add(commentMapper.toDTO(comment));
                }
            }});
        }

        return dto;
    }

    public Post fromDTO(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setUser(userRepository.findBy(dto.getUserId()));
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setImage(dto.getImage());
        post.setDate(dto.getDate());

        if (dto.getTags() != null) {
            post.setTags(new HashSet<Tag>(){{
                for (TagDTO tag : dto.getTags()) {
                    add(tagMapper.fromDTO(tag));
                }
            }});
        }

        return post;
    }
}
