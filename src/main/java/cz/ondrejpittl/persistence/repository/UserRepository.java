package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.User;
import org.apache.deltaspike.data.api.*;

import java.util.List;



@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long> {

    @Query(max = 1, singleResult = SingleResultType.OPTIONAL)
    User findById(Long id);

    @Query(max = 1, singleResult = SingleResultType.OPTIONAL)
    User findByIdAndDisabledEqual(Long id, boolean disabled);

    List<User> findByDisabledEqualOrderByFirstNameAsc(boolean disabled);

    @Query(value = "select u from User u where u.slug like :slug", max = 1, singleResult = SingleResultType.OPTIONAL)
    User findBySlug(@QueryParam("slug") String slug);

    @Query(max = 1, singleResult = SingleResultType.OPTIONAL)
    User findByEmailLike(String email);

    User removeById(Long id);

    @Query("select count(u) from User u where u.slug like :slug")
    Long countUsersBySlug(@QueryParam("slug") String slug);

    @Query("select count(u) from User u where u.email like :email")
    Long countUsersByEmail(@QueryParam("email") String email);

    @Query("select count(u) from User u where u.email like :email and u.password like :password")
    Long countUsers(@QueryParam("email") String email, @QueryParam("password") String password);

    @Query("select count(u) from User u where u.id = :id")
    Long countUsers(@QueryParam("id") Long id);

    //@Query("select u from User as u join u.friendedBy as fb")
    //List<User> test();
}
