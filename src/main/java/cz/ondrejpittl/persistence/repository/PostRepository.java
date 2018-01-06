package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Post;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface PostRepository extends EntityRepository<Post, Long> {

    Integer removeById(Long id);


}
