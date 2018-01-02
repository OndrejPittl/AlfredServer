package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "POSTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Post {

    /**
     * Post ID.
     * INT, PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    /**
     * User.
     * INT, FK
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    //@JsonBackReference(value = "user-posts")
    private User user;

    /**
     * Post title.
     * VARCHAR(255)
     */
    private String title;

    /**
     * Post body.
     * TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String body;

    /**
     * Post image.
     * VARCHAR(255)
     */
    private String image;

    /**
     * Post title.
     * DATETIME
     */
    private Date date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "POSTS_HAVE_TAGS",
        joinColumns = {
            @JoinColumn(
                name = "postId",
                referencedColumnName = "id"
            )
        },
        inverseJoinColumns = {
            @JoinColumn(
                name = "tagId",
                referencedColumnName = "id"
            )
        }
    )
    private Set<Tag> tags;



    public Post() { }

    public Post(String title, String body, String image, Date date) {
        this(title, body, image, date, null, null);
    }

    public Post(String title, String body, String image, Date date, User user) {
        this(title, body, image, date, user, null);
    }

    public Post(String title, String body, String image, Date date, User user, Set<Tag> tags) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.date = date;
        this.user = user;
        this.tags = tags;
    }

    /*
    public Post(Long id, String title, String body, String image, Date date, User user) {
        this(title, body, image, date, user);
        this.id = id;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
