package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Friendship;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Rating;
import org.apache.deltaspike.data.api.*;

import java.util.List;

@Repository
public interface RatingRepository extends EntityRepository<Rating, Long> {

    Integer removeById(Long id);

    @Modifying
    @Query("delete from Rating as r where r.id in (:ids)")
    int removePostRatings(@QueryParam("ids") List<Long> ids);

}
