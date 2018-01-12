package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.business.annotations.AllowedValues;
import cz.ondrejpittl.business.annotations.PasswordEquality;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.validation.CreateGroup;
import cz.ondrejpittl.business.validation.ModifyGroup;
import cz.ondrejpittl.persistence.domain.Sex;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;


@PasswordEquality(
    groups = { CreateGroup.class, ModifyGroup.class },
    message = "user.confirmPassword"
)
public class UserDTO {

    /**
     * User ID.
     */
    private Long id;

    /**
     * First name.
     */
    @NotNull(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.firstName"
    )
    @Size(
        groups = { CreateGroup.class, ModifyGroup.class },
        min = 1, max = 50,
        message = "user.firstName"
    )
    private String firstName;

    /**
     * Last name.
     */
    @NotNull(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.lastName"
    )
    @Size(
        groups = { CreateGroup.class, ModifyGroup.class },
        min = 1, max = 50,
        message = "user.lastName"
    )
    private String lastName;

    /**
     * E-mail.
     */
    @NotNull(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.email.pattern"
    )
    @Email(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.email.pattern"
    )
    @UniqueEmail(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.email.unique"
    )
    private String email;

    /**
     * Gender.
     */
    @NotNull(
        groups = { CreateGroup.class },
        message = "required"
    )
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
    @NotNull(
        groups = { CreateGroup.class },
        message = "user.password"
    )
    @Pattern(
        groups = { CreateGroup.class, ModifyGroup.class },
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,50}$",
        message = "user.password"
    )
    private String password;

    /**
     * Password confirmation.
     */
    @NotNull(
        groups = { CreateGroup.class },
        message = "user.confirmPassword.empty"
    )
    private String confirmPassword;


    private String token;


    private Set<PostDTO> posts;





    public UserDTO() {

    }

    public UserDTO(
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    /*
    @AssertTrue (
        groups = { CreateGroup.class, ModifyGroup.class }
    )
    private boolean checkPwdEquality() {
        boolean rs = this.password == null || this.password.equals(this.confirmPassword);
        Dev.print("Checking passwords");
        Dev.printObject(this.password);
        Dev.printObject(this.confirmPassword);
        Dev.printObject(rs);
        return rs;
    }
    */

}
