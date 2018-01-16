package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.cfg.Config;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;
import org.apache.deltaspike.data.api.Modifying;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import java.util.*;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    @Inject
    PostRepository postRepository;

    @Inject
    PostRestMapper postMapper;

    @Inject
    TagService tagService;

    @Inject
    UserService userService;

    @Inject
    AuthService authService;

    @Inject
    CommentService commentService;






    public List<Post> getPosts() {
        return this.postRepository.findAll();
    }

    public List<Post> getPosts(int offset) {
        return this.postRepository.findAllOrderByDateDesc(offset, Config.POST_LIMIT);
    }

    public List<Post> getUserPosts(Long userId) {
        User u = this.userService.getUser(userId);
        return new ArrayList<>(u.getPosts());
    }

    public List<Post> getUserPosts(Long userId, int offset) {
        User u = this.userService.getUser(userId);
        List<Long> l = new LinkedList<>();
        l.add(u.getId());
        return this.postRepository.findPostsOfUsers(l, offset, Config.POST_LIMIT);
    }

    public List<Post> getUserRatedPosts(int offset) {
        User u = this.userService.getAuthenticatedUser();
        return this.postRepository.findUserRatedPosts(u.getId(), offset, Config.POST_LIMIT);
    }

    public List<Post> getUserFriendsPosts(int offset) {
        User user = this.userService.getAuthenticatedUser();
        List<User> friends = user.getFriends();

        List<Long> ids = new LinkedList<>();
        for (User u : friends) ids.add(u.getId());

        return this.postRepository.findPostsOfUsers(ids, offset, Config.POST_LIMIT);
    }

    public List<Post> getTagPosts(String tag, int offset) {
        /* Tag t = this.tagService.getTag(tag);
        if(t == null) { throw new WebApplicationException("No Tag \"" + tag + "\" was found.", Response.Status.NOT_FOUND); }
        return new ArrayList<>(t.getPosts()); */

        //return this.postRepository.findTagPosts(tag, offset, Config.POST_LIMIT);
        return this.postRepository.findPostFilteredByTag(tag, offset, Config.POST_LIMIT);
    }

    public List<Post> getPosts(int offset, String tag, Long minRating, boolean hasImage) {
        if(tag != null && minRating != null && hasImage) {
            return this.postRepository.findPostFilteredByTagRatingPhoto(tag, minRating, offset, Config.POST_LIMIT);
        } else if(tag != null && minRating != null) {
            return this.postRepository.findPostFilteredByTagRating(tag, minRating, offset, Config.POST_LIMIT);
        } else if(tag != null && hasImage) {
            return this.postRepository.findPostFilteredByTagPhoto(tag, offset, Config.POST_LIMIT);
        } else if(minRating != null && hasImage) {
            return this.postRepository.findPostFilteredByRatingPhoto(minRating, offset, Config.POST_LIMIT);
        } else if(tag != null) {
            return this.postRepository.findPostFilteredByTag(tag, offset, Config.POST_LIMIT);
        } else if(minRating != null) {
            return this.postRepository.findPostFilteredByRating(minRating, offset, Config.POST_LIMIT);
        } else if(hasImage) {
            return this.postRepository.findPostFilteredByPhoto(offset, Config.POST_LIMIT);
        }

        return this.postRepository.findAllOrderByDateDesc(offset, Config.POST_LIMIT);
    }

    public Post getPost(Long id) {
        return this.postRepository.findBy(id);
    }

    @Transactional
    public List<Post> createPost(PostDTO dto) {
        Post p = postMapper.fromDTO(dto);
        User u = this.userService.getAuthenticatedUser();

        p.setUser(u);

        this.postRepository.saveAndFlush(p);
        return this.getPosts(0);
    }

    @Transactional
    public List<Post> removePost(Long id) {
        Dev.print("POST DELETE: Looking for Post ID " + id);

        Post post = this.postRepository.findBy(id);
        Set<Tag> tags = post.getTags();

        List<Long> comIDs = new ArrayList<>();
        for(Comment c : post.getComments()) {
            comIDs.add(c.getId());
        }

        this.commentService.removePostComments(comIDs);

        Dev.print("POST DELETE: Removing Post ID " + id);

        this.postRepository.removeById(id);
        this.postRepository.flush();

        this.tagService.removeOrphans(tags);

        return this.getPosts(0);
    }

    @Transactional
    public Post modifyPost(Long id, PostDTO dto) {
        boolean modified = false;
        Post p = this.postMapper.fromDTO(dto);
        Set<Tag> prevTags = null;

        Dev.print("POST PUT: Looking fot Post ID " + id);
        Post orig = this.getPost(id);
        Dev.print("POST PUT: Modifying Post ID " + id);


        if(orig == null) {
            return null;
        }

        if(!p.getTitle().equals(orig.getTitle())) {
            orig.setTitle(p.getTitle());
            modified = true;
        }

        if(!p.getBody().equals(orig.getBody())) {
            orig.setBody(p.getBody());
            modified = true;
        }

        if(!p.getImage().equals(orig.getImage())) {
            orig.setImage(p.getImage());
            modified = true;
        }

        if(p.getTags() != null) {
            prevTags = new HashSet<>(orig.getTags());

            orig.setTags(new HashSet<Tag>() {{
                Iterator<Tag> it = p.getTags().iterator();
                while(it.hasNext()) {
                    Tag t = tagService.getOrCreateTag(it.next().getName());
                    add(t);
                }
            }});

            modified = true;
        }


        if(modified) {
            // modification –> update date
            orig.setLastModified(new Date(System.currentTimeMillis()));
        }

        this.postRepository.saveAndFlush(orig);


        if(prevTags != null) {
            this.tagService.removeOrphans(prevTags);
        }

        return orig;
    }

    public boolean checkPostExists(Long id) {
        return this.postRepository.countPosts(id) == 1;
    }
}
