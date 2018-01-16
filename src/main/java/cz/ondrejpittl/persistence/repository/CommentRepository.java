package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Comment;
import org.apache.deltaspike.data.api.*;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends EntityRepository<Comment, Long> {

    Integer removeById(Long id);

    //@Query("select p from Post as p join p.user as u where u.id in (:ids) order by p.date desc")
    //@Query("delete c from Comment as c join c.post as p where p.id = :id")
    //@Query("delete from Comment as c where c.id in (select c.id from Comment as c join c.post as p where p.id = :id)")

    @Modifying
    @Query("delete from Comment as c where c.id in (:ids)")
    Integer removePostComments(@QueryParam("ids") List<Long> ids);
}
