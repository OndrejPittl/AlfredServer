package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.persistence.domain.Friendship;
import cz.ondrejpittl.persistence.domain.User;
import org.apache.deltaspike.data.api.*;

import java.util.List;

@Repository
public interface FriendshipRepository extends EntityRepository<Friendship, Long> {

    //@Query("select f from Friendship f where f.user = :user and f.accepted = 1")
    //List<Friendship> findFriendships(@QueryParam(":user") User user);

    //@Query("select f from Friendship f where f.userId = :uid and f.accepted = 0")
    //List<Friendship> findFriendRequests(@QueryParam(":uid") Long uid);

    Integer removeById(Long id);


    //QueryResult<Friendship> FindAllFriendships();

}
