package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Sex;

import java.util.List;
import java.util.Set;

public class UserDTO {

    /**
     * User ID.
     */
    private Long id;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * E-mail.
     */
    private String email;

    /**
     * Gender.
     */
    private Sex sex;

    /**
     * Profile photo.
     */
    private String photo;

    /**
     * URL profile slug.
     */
    private String slug;

    /**
     * Password.
     */
    private String password;


    private Set<PostDTO> posts;



    public UserDTO() {

    }

    public UserDTO(
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

    public Set<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostDTO> posts) {
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
