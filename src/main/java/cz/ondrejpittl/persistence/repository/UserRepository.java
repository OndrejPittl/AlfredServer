package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.User;
import org.apache.deltaspike.data.api.*;

import java.util.List;



@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long> {

    List<User> findByDisabledEqualOrderByFirstNameAsc(boolean disabled);

    User findFirst1BySlugLike(String slug);

    @Query(max = 1, singleResult = SingleResultType.OPTIONAL)
    User findByEmailLike(String email);

    User removeById(Long id);

    @Query("select count(u) from User u where u.slug like :slug")
    Long countUsersBySlug(@QueryParam("slug") String slug);

    @Query("select count(u) from User u where u.email like :email and u.password like :password")
    Long countUsers(@QueryParam("email") String email, @QueryParam("password") String password);



}
