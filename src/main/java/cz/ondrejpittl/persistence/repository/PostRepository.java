package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Post;
import org.apache.deltaspike.data.api.*;

import java.util.List;

@Repository
public interface PostRepository extends EntityRepository<Post, Long> {

    List<Post> findAllOrderByDate(@FirstResult int start, @MaxResults int pageSize);

    List<Post> findAllOrderByDate();

    Integer removeById(Long id);

    @Query("select count(p) from Post p where p.id = :id")
    Long countPosts(@QueryParam("id") Long id);

}
