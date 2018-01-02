package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.TagDTO;

import java.util.List;

public interface CommentService {

    public List<Comment> getAllComments();

    public Comment getComment(Long id);

    public Comment createComment(CommentDTO comment);

}
