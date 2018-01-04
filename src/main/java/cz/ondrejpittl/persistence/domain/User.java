package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {

    /**
     * User ID. INT, PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    /**
     * First name. VARCHAR(255)
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Last name. VARCHAR(255)
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * E-mail. VARCHAR(255)
     */
    @Column(nullable = false)
    private String email;

    /**
     * Gender. VARCHAR(255)
     */
    @Enumerated(EnumType.STRING)
    private Sex sex;

    /**
     * Profile photo. TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String photo;

    /**
     * URL profile slug. VARCHAR(255)
     */
    @Column(nullable = false)
    private String slug;

    /**
     * Password.
     */
    private String password;

    @Column(nullable=false, columnDefinition="boolean default false")
    private boolean disabled;

    /**
     * User's posts.
     */
    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        cascade = { CascadeType.MERGE }
    )
    private Set<Post> posts = new HashSet<Post>();


    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();



    public User() {}

    public User(
            String firstName,
            String lastName,
            String email,
            Sex sex,
            String photo,
            String slug,
            String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.photo = photo;
        this.slug = slug;
        this.password = password;
        this.disabled = false;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addPost(Post post) {
        // post –> set
        // user –> post
        // ===> consistency
        this.posts.add(post);
        post.setUser(this);
    }

    public void addPosts(Set<Post> posts) {
        for(Post post : posts) {
            this.addPost(post);
        }
    }
}
