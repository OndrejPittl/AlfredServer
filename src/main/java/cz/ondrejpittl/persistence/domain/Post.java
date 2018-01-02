package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.Set;


@Entity
@Table(name = "POSTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Post {

    /**
     * Post ID. INT, PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    /**
     * User. INT, FK
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    /**
     * Post title. VARCHAR(255)
     */
    private String title;

    /**
     * Post body. TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String body;

    /**
     * Post image. TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String image;

    /**
     * Post title. DATETIME
     */
    private Date date;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
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

    @OneToMany(
        mappedBy = "post",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private Set<Comment> comments;



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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
