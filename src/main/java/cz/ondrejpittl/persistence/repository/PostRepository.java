package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Post;
import org.apache.deltaspike.data.api.*;

import java.util.List;

@Repository
public interface PostRepository extends EntityRepository<Post, Long> {

    // select *, (select count(rr.id) from posts pp left outer join ratings rr on pp.id = rr.postId where pp.id = p.id group by pp.id) as rating from posts p left outer join ratings r on p.id = r.postId having rating >= 0

    // select *, (select count(rr.id) from posts pp left outer join ratings rr on pp.id = rr.postId where pp.id = p.id group by pp.id) as rating, (select group_concat(t_2.name separator ', ') from posts p_2 left outer join posts_have_tags pht_2 on p_2.id = pht_2.postId left outer join tags t_2 on t_2.id = pht_2.tagId where p_2.id = p.id group by p_2.id) as tags from posts p left outer join ratings r on p.id = r.postId left outer join posts_have_tags pht on p.id = pht.postId left outer join tags t on t.id = pht.tagId having rating >= 0
    // select p.* from posts p left outer join ratings r on p.id = r.postId left outer join posts_have_tags pht on p.id = pht.postId left outer join tags t on t.id = pht.tagId where (select count(rr.id) from posts pp left outer join ratings rr on pp.id = rr.postId where pp.id = p.id group by pp.id) >= 0 and (select group_concat(t_2.name separator ', ') from posts p_2 left outer join posts_have_tags pht_2 on p_2.id = pht_2.postId left outer join tags t_2 on t_2.id = pht_2.tagId where p_2.id = p.id group by p_2.id) like '%harry%' group by p.id

    // ----- HQL:

    // rating > X
    // (select count(r_rating.id) from Post as p_rating left outer join p_rating.rating as r_rating where p_rating.id = p.id group by p_rating.id) >= :rating

    // tag like X
    // :tag in (select t_tag.name from Post as p_tag left outer join p_tag.tags as t_tag where p_tag.id = p.id group by p_tag.id)

    // has photo
    // where p.image isNotNull


    List<Post> findAllOrderByDate();



    Integer removeById(Long id);

    @Query("select count(p) from Post p where p.id = :id")
    Long countPosts(@QueryParam("id") Long id);


    @Query("select p from Post as p join p.user as u where u.id in (:ids) order by p.date desc")
    List<Post> findPostsOfUsers(@QueryParam("ids") List<Long> ids, @FirstResult int start, @MaxResults int pageSize);

    @Query("select p from Post as p join p.rating r join r.user u where u.id = :id order by p.date desc")
    List<Post> findUserRatedPosts(@QueryParam("id") Long id, @FirstResult int start, @MaxResults int pageSize);

    @Query("select p from Post as p inner join p.tags as t where t.name like :tag order by p.date")
    List<Post> findTagPosts(@QueryParam("tag") String tag, @FirstResult int start, @MaxResults int pageSize);


    // general
    List<Post> findAllOrderByDate(@FirstResult int start, @MaxResults int pageSize);

    // general – where tag + rating + photo
    @Query("select p from Post as p where :tag in (select t_tag.name from Post as p_tag left outer join p_tag.tags as t_tag where p_tag.id = p.id group by p_tag.id) and (select count(r_rating.id) from Post as p_rating left outer join p_rating.rating as r_rating where p_rating.id = p.id group by p_rating.id) >= :rating and p.image is not null order by p.date")
    List<Post> findPostFilteredByTagRatingPhoto(@QueryParam("tag") String tag, @QueryParam("rating") Long rating, @FirstResult int start, @MaxResults int pageSize);

    // general – where tag + rating
    @Query("select p from Post as p where :tag in (select t_tag.name from Post as p_tag left outer join p_tag.tags as t_tag where p_tag.id = p.id group by p_tag.id) and (select count(r_rating.id) from Post as p_rating left outer join p_rating.rating as r_rating where p_rating.id = p.id group by p_rating.id) >= :rating order by p.date")
    List<Post> findPostFilteredByTagRating(@QueryParam("tag") String tag, @QueryParam("rating") Long rating, @FirstResult int start, @MaxResults int pageSize);

    // general – where tag + photo
    @Query("select p from Post as p where :tag in (select t_tag.name from Post as p_tag left outer join p_tag.tags as t_tag where p_tag.id = p.id group by p_tag.id) and p.image is not null order by p.date")
    List<Post> findPostFilteredByTagPhoto(@QueryParam("tag") String tag, @FirstResult int start, @MaxResults int pageSize);

    // general – where rating + photo
    @Query("select p from Post as p where (select count(r_rating.id) from Post as p_rating left outer join p_rating.rating as r_rating where p_rating.id = p.id group by p_rating.id) >= :rating and p.image is not null order by p.date")
    List<Post> findPostFilteredByRatingPhoto(@QueryParam("rating") Long rating, @FirstResult int start, @MaxResults int pageSize);

    // general – where tag
    @Query("select p from Post as p where :tag in (select t_tag.name from Post as p_tag left outer join p_tag.tags as t_tag where p_tag.id = p.id group by p_tag.id) order by p.date")
    List<Post> findPostFilteredByTag(@QueryParam("tag") String tag, @FirstResult int start, @MaxResults int pageSize);

    // general – where rating
    @Query("select p from Post as p where (select count(r_rating.id) from Post as p_rating left outer join p_rating.rating as r_rating where p_rating.id = p.id group by p_rating.id) >= :rating order by p.date")
    List<Post> findPostFilteredByRating(@QueryParam("rating") Long rating, @FirstResult int start, @MaxResults int pageSize);

    // general – where photo
    @Query("select p from Post as p where p.image is not null order by p.date")
    List<Post> findPostFilteredByPhoto(@FirstResult int start, @MaxResults int pageSize);
}
