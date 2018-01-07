package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.FriendshipRepository;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.RatingRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.CommentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@ApplicationScoped
public class RatingServiceImpl implements RatingService {

    @Inject
    @AuthenticatedUser
    Identity authenticatedUser;

    @Inject
    UserService userService;

    @Inject
    PostService postService;

    @Inject
    RatingRepository ratingRepository;



    public Set<Post> getRatedPosts() {
        User user = this.userService.getAuthenticatedUser();
        return new HashSet<Post>() {{
            for(Rating r : user.getRated()) {
                add(r.getPost());
            }
        }};
    }

    @Transactional
    public Post registerRating(Long postId) {
        User user = this.userService.getAuthenticatedUser();
        Post post = this.postService.getPost(postId);
        Rating r =  new Rating(user, post);
        this.ratingRepository.save(r);
        return null;
    }

    @Transactional
    public Post cancelRating(Long postId) {
        Post p = this.postService.getPost(postId);
        Long rId = -1l;

        for(Rating r : p.getRating()) {
            if(r.getPost().getId().equals(postId)) {
                rId = r.getPost().getId();
                break;
            }
        }

        if(rId != -1) {
            Dev.print("Removing rating ID " + rId);
            this.ratingRepository.removeById(rId);
        }

        return null;
    }
}
