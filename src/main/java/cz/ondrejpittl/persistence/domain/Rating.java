package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "RATINGS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable=false)
    private Date date = new Date();

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "postId", nullable = false)
    private Post post;


    public Rating() {}


    public Rating(User user, Post post) {
        this.user = user;
        this.post = post;
        this.date = new Date(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
