package cz.ondrejpittl.rest.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.ondrejpittl.persistence.domain.Rating;
import cz.ondrejpittl.persistence.domain.Tag;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PostDTO {

    /**
     * Post ID.
     */
    private Long id;

//    /**
//     * User ID.
//     */
    //private Long userId;

    /**
     * Post title.
     */
    private String title;

    /**
     * Post body.
     */
    private String body;

    /**
     * Post image.
     */
    private String image;

    /**
     * Post date.
     */
    private Date date = new Date();

    /**
     * Post last modification.
     */
    private Date lastModified;


    private UserDTO user;


    private Set<TagDTO> tags;

    private Set<CommentDTO> comments;

    /*
    private Long rating;

    private boolean userRated;
    */

    private List<Long> rating;



    public PostDTO() { }

    /*
    public PostDTO(Long id, Long userId, String title, String body, String image, Date date) {
        this(id, userId, title, body, image, date, null, null);
    }

    public PostDTO(Long id, Long userId, String title, String body, String image, Date date, Set<TagDTO> tags, Set<CommentDTO> comments) {
        this.id = id;
        //this.userId = userId;
        this.title = title;
        this.body = body;
        this.image = image;
        this.date = date;
        this.tags = tags;
        this.comments = comments;
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<Long> getRating() {
        return rating;
    }

    public void setRating(List<Long> rating) {
        this.rating = rating;
    }

    /*
    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public boolean isUserRated() {
        return userRated;
    }

    public void setUserRated(boolean userRated) {
        this.userRated = userRated;
    }

    */

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
