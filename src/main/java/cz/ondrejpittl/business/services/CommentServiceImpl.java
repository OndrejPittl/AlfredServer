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
import java.util.*;

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
    public List<Comment> createComment(Long postId, CommentDTO comment) {
        Comment c = commentMapper.fromDTO(comment);

        User u = this.userService.getUser(this.authenticatedUser.getUserId());
        Post p = this.postService.getPost(postId);

        c.setUser(u);
        c.setPost(p);

        commentRepository.saveAndFlush(c);

        return new ArrayList<>(this.postService.getPost(postId).getComments());
    }

    @Transactional
    public List<Comment> modifyComment(Long id, CommentDTO dto) {
        Comment comment = commentRepository.findBy(id);
        Comment c = commentMapper.fromDTO(dto);
        Long postId = comment.getPost().getId();
        boolean modified = false;

        if(!c.getBody().equals(comment.getBody())) {
            comment.setBody(c.getBody());
            modified = true;
        }

        if(modified) {
            comment.setLastModified(new Date());
        }

        this.commentRepository.saveAndFlush(comment);
        return new ArrayList<>(this.postService.getPost(postId).getComments());
    }


    public void removePostComments(List<Long> comIDs) {
        int num = this.commentRepository.removePostComments(comIDs);
        this.commentRepository.flush();
        //Dev.print("Deleted " + num + " comments of post.");
    }

    @Transactional
    public List<Comment> removeComment(Long commentId) {
        //Dev.print("COMMENT DELETE: Removing comment ID " + commentId);

        Comment c = this.commentRepository.findBy(commentId);
        Long postId = c.getPost().getId();
        this.commentRepository.removeById(commentId);
        this.commentRepository.flush();
        Set<Comment> comments = this.postService.getPost(postId).getComments();
        return new ArrayList<>(comments);
    }
}
