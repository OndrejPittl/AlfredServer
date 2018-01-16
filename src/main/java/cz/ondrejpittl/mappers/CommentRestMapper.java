package cz.ondrejpittl.mappers;

import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.repository.CommentRepository;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class CommentRestMapper {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PostRepository postRepository;

    @Inject
    private UserRestMapper userMapper;


    public List<CommentDTO> toDTOs(List<Comment> comments) {
        List<CommentDTO> dtos = new ArrayList<>();

        for (Comment comment : comments) {
            dtos.add(this.toDTO(comment));
        }

        return dtos;
    }

    public CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setDate(comment.getDate());
        dto.setLastModified(comment.getLastModified());
        dto.setUser(userMapper.toDTO(comment.getUser(), false));
        return dto;
    }

    public Comment fromDTO(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setBody(dto.getBody());
        //comment.setDate(dto.getDate());
        //comment.setLastModified(dto.getLastModified());
        return comment;
    }
}
