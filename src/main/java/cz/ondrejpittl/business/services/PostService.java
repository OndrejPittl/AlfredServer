package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.rest.dtos.PostDTO;

import java.util.List;

public interface PostService {

    List<Post> getPosts();

    //List<Post> getPosts(int page);
    List<Post> getPosts(int offset);

    List<Post> getPosts(int offset, String tag, Long minRating, boolean hasImage);

    List<Post> getUserPosts(Long userId);

    List<Post> getUserPosts(Long userId, int offset);

    //List<Post> getPostsFrom(Long id);

    Post getPost(Long id);

    List<Post> createPost(PostDTO dto);

    List<Post> removePost(Long id);

    Post modifyPost(Long id, PostDTO dto);

    boolean checkPostExists(Long id);

    List<Post> getTagPosts(String tag, int offset);

    List<Post> getUserRatedPosts(int offset);

    List<Post> getUserFriendsPosts(int offset);

}
