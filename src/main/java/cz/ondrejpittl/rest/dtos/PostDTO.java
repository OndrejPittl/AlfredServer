package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.persistence.domain.Tag;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PostDTO {

    /**
     * Post ID.
     */
    private Long id;

    /**
     * User ID.
     */
    private Long userId;

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
     * Post title.
     */
    private Date date;

    private Set<Tag> tags;



    public PostDTO() {

    }

    public PostDTO(Long id, Long userId, String title, String body, String image, Date date) {
        this(id, userId, title, body, image, date, null);
    }

    public PostDTO(Long id, Long userId, String title, String body, String image, Date date, Set<Tag> tags) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.image = image;
        this.date = date;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
