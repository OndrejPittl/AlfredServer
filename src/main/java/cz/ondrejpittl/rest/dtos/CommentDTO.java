package cz.ondrejpittl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;

import javax.persistence.*;
import java.util.Date;


public class CommentDTO {

    private Long id;

    private String body;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModified = null;

    private UserDTO user;



    public CommentDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    /*
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
    */
}
