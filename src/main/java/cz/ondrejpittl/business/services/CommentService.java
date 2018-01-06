package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    //List<Comment> getAllPostComments(Long postId);

    Comment getComment(Long id);

    Comment createComment(Long postId, CommentDTO comment);

    Comment removeComment(Long commentId);

    Comment modifyComment(Long id, CommentDTO dto);
}
