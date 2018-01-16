package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import java.util.List;
import java.util.Set;

public interface CommentService {

    List<Comment> getAllComments();

    //List<Comment> getAllPostComments(Long postId);

    Comment getComment(Long id);

    List<Comment> createComment(Long postId, CommentDTO comment);

    List<Comment> removeComment(Long commentId);

    List<Comment> modifyComment(Long id, CommentDTO dto);

    void removePostComments(List<Long> comIDs);
}
