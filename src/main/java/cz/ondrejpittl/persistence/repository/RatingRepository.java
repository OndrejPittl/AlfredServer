package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Friendship;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Rating;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface RatingRepository extends EntityRepository<Rating, Long> {

    Integer removeById(Long id);

}
