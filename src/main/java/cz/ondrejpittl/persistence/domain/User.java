package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    /**
     * User ID.
     * INT, PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    /**
     * First name.
     * VARCHAR(255)
     */
    private String firstName;

    /**
     * Last name.
     * VARCHAR(255)
     */
    private String lastName;

    /**
     * E-mail.
     * VARCHAR(255)
     */
    private String email;

    /**
     * Gender.
     * VARCHAR(255)
     */
    @Enumerated(EnumType.STRING)
    private Sex sex;

    /**
     * Profile photo.
     * VARCHAR(255) TODO: víc?
     */
    @Column(columnDefinition = "TEXT")
    private String photo;

    /**
     * URL profile slug.
     * VARCHAR(255)
     */
    private String slug;

    /**
     * Password.
     * CHAR(32) – MD5 produces 32 B hash
     */
    @Column(columnDefinition = "CHAR(32)")
    private String password;

    /**
     * User's posts.
     */
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Post> posts;



    public User() {

    }

    public User(
            Long id,
            String firstName,
            String lastName,
            String email,
            Sex sex,
            String photo,
            String slug,
            String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.photo = photo;
        this.slug = slug;
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
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


}
