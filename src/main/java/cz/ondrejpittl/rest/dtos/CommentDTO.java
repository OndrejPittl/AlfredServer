package cz.ondrejpittl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;

import javax.persistence.*;
import java.util.Date;


public class CommentDTO {

    private Long id;

    private String body;

    private Date date;

    private Long userId;

    private Long postId;




    public CommentDTO() {}

    public CommentDTO(Long id, String body, Date date, Long userId, Long postId) {
        this.id = id;
        this.body = body;
        this.date = date;
        this.userId = userId;
        this.postId = postId;
    }

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
}
