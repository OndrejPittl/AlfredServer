package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;

import java.util.List;
import java.util.Set;

public interface RatingService {

    Set<Post> getRatedPosts();

    Post createRating(Long postId);

    Post cancelRating(Long postId);

    void removePostRatings(List<Long> rIDs);

}
