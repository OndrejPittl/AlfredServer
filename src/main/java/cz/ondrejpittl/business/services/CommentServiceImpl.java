package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.repository.CommentRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    CommentRepository commentRepository;

    @Inject
    CommentRestMapper commentMapper;

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    public Comment getComment(Long id) {
        return this.commentRepository.findBy(id);
    }


    @Transactional
    public Comment createComment(CommentDTO comment) {
        return commentRepository.save(commentMapper.fromDTO(comment));
    }
}
