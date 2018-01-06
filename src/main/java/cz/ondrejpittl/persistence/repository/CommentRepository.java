package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Comment;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface CommentRepository extends EntityRepository<Comment, Long> {

    Integer removeById(Long id);

}
