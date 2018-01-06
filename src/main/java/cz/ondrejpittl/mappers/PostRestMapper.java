package cz.ondrejpittl.mappers;

import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;
import org.omg.IOP.TAG_RMI_CUSTOM_MAX_STREAM_FORMAT;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class PostRestMapper {

    @Inject
    UserRepository userRepository;

    @Inject
    TagRestMapper tagMapper;

    @Inject
    TagService tagService;

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
        dto.setLastModified(post.getLastModified());

        if (post.getTags() != null) {
            dto.setTags(new HashSet<TagDTO>(){{
                for (Tag tag : post.getTags()) {
                    add(tagMapper.toDTO(tag));
                }
            }});
        }

        /*
        if (post.getComments() != null) {
            dto.setComments(new HashSet<CommentDTO>(){{
                for (Comment comment : post.getComments()) {
                    add(commentMapper.toDTO(comment));
                }
            }});
        }
        */

        return dto;
    }

    public Post fromDTO(PostDTO dto) {
        Post post = new Post();
        if(dto.getTitle() != null)  post.setTitle(dto.getTitle());
        if(dto.getBody() != null)   post.setBody(dto.getBody());
        if(dto.getImage() != null)  post.setImage(dto.getImage());
        if(dto.getDate() != null)   post.setDate(dto.getDate());
        if(dto.getLastModified() != null)   post.setLastModified(dto.getLastModified());

        if (dto.getTags() != null) {
            post.setTags(new HashSet<Tag>(){{
                for (TagDTO tag : dto.getTags()) {
                    add(tagMapper.fromDTO(tag));
                }
            }});
        }

        if(dto.getUserId() != null) {
            User user = userRepository.findBy(dto.getUserId());
            user.addPost(post);
        }


        /*
        Comment c1 = new Comment("abcd", new Date());
        post.addComment(c1);
        c1.setUser(user);
        */

        return post;
    }
}
