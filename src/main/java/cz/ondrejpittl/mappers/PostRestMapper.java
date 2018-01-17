package cz.ondrejpittl.mappers;

import cz.ondrejpittl.business.services.AuthService;
import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
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

    @Inject
    UserRestMapper userMapper;

    @Inject
    AuthService authService;



    public List<PostDTO> toDTOs(List<Post> posts) {
        return this.toDTOs(posts, true);
    }

    public List<PostDTO> toDTOs(List<Post> posts, boolean deep) {
        List<PostDTO> dtos = new ArrayList<>();

        for (Post post : posts) {
            dtos.add(this.toDTO(post, deep));
        }

        return dtos;
    }

    public PostDTO toDTO(Post post) {
        return this.toDTO(post, true);
    }

    public PostDTO toDTO(Post post, boolean deep) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setUser(this.userMapper.toDTO(post.getUser(), false));
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setImage(post.getImage());
        dto.setDate(post.getDate());
        dto.setLastModified(post.getLastModified());


        if (post.getTags() != null) {
            Set<TagDTO> tags = new LinkedHashSet<>();
            for (Tag tag : post.getTags()) {
                tags.add(tagMapper.toDTO(tag));
            }
            dto.setTags(tags);
        }

        if (post.getRating() != null) {
            List<Long> ratings = new ArrayList<>();
            for(Rating r : post.getRating()) {
                ratings.add(r.getUser().getId());
            }
            dto.setRating(ratings);
        }



        if(!deep) return dto;



        if (post.getComments() != null) {
            Set<CommentDTO> comments = new LinkedHashSet<>();
            for (Comment comment : post.getComments()) {
                comments.add(commentMapper.toDTO(comment));
            }
            dto.setComments(comments);
        }

        return dto;
    }

    public Post fromDTO(PostDTO dto) {
        Post post = new Post();
        if(dto.getTitle() != null)  post.setTitle(dto.getTitle());
        if(dto.getBody() != null)   post.setBody(dto.getBody());
        if(dto.getImage() != null)  post.setImage(dto.getImage());
        if(dto.getDate() != null)   post.setDate(dto.getDate());

        if (dto.getTags() != null) {
            post.setTags(new HashSet<Tag>(){{
                for (TagDTO tag : dto.getTags()) {
                    add(tagMapper.fromDTO(tag));
                }
            }});
        }

        /*
        if(dto.getUserId() != null) {
            User user = userRepository.findBy(dto.getUserId());
            user.addPost(post);
        }
        */

        /*
        Comment c1 = new Comment("abcd", new Date());
        post.addComment(c1);
        c1.setUser(user);
        */

        return post;
    }
}
