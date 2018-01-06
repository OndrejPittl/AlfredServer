package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.CommentRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    @AuthenticatedUser
    Identity authenticatedUser;


    @Inject
    CommentRepository commentRepository;

    @Inject
    CommentRestMapper commentMapper;

    @Inject
    UserService userService;

    @Inject
    PostService postService;


    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    public Comment getComment(Long id) {
        return this.commentRepository.findBy(id);
    }


    @Transactional
    public Comment createComment(Long postId, CommentDTO comment) {
        Comment c = commentMapper.fromDTO(comment);

        User u = this.userService.getUser(this.authenticatedUser.getUserId());
        Post p = this.postService.getPost(postId);

        c.setUser(u);
        c.setPost(p);

        return commentRepository.save(c);
    }

    @Transactional
    public Comment modifyComment(Long id, CommentDTO dto) {
        Comment comment = commentRepository.findBy(id);
        Comment c = commentMapper.fromDTO(dto);
        boolean modified = false;

        if(c.getBody() != comment.getBody()) {
            comment.setBody(c.getBody());
            modified = true;
        }

        if(modified) {
            comment.setLastModified(new Date());
        }

        return this.commentRepository.save(comment);
    }

    @Transactional
    public Comment removeComment(Long commentId) {
        Dev.print("COMMENT DELETE: Removing comment ID " + commentId);
        this.commentRepository.removeById(commentId);
        return null;
    }
}
