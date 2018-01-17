package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.business.annotations.CaptchaVerification;
import cz.ondrejpittl.business.annotations.PasswordEquality;
import cz.ondrejpittl.business.annotations.UniqueAssignedEmail;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.validation.CreateGroup;
import cz.ondrejpittl.business.validation.ModifyGroup;
import cz.ondrejpittl.persistence.domain.Sex;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
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
        groups = { CreateGroup.class },
        message = "user.email.unique"
    )
    @UniqueAssignedEmail(
        groups = { ModifyGroup.class },
        message = "user.email.unique"
    )
    private String email;

    /**
     * Gender.
     */
    @NotNull(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "required"
    )
    private Sex sex;

    /**
     * Profile photo.
     */
    private String photo = "https://goo.gl/uJ7SMr";

    /**
     * URL profile slug.
     */
    private String slug;


    @Past(
        groups = { CreateGroup.class, ModifyGroup.class },
        message = "user.birth.past"
    )
    private Date birth;

    @NotNull(
        groups = { CreateGroup.class },
        message = "captcha"
    )
    @CaptchaVerification(
        message = "captcha"
    )
    private String captcha;

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

    private List<UserDTO> friends;

    private List<UserDTO> inFReqs;

    private List<UserDTO> outFReqs;




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

    public List<UserDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDTO> friends) {
        this.friends = friends;
    }

    public List<UserDTO> getInFReqs() {
        return inFReqs;
    }

    public void setInFReqs(List<UserDTO> inFReqs) {
        this.inFReqs = inFReqs;
    }

    public List<UserDTO> getOutFReqs() {
        return outFReqs;
    }

    public void setOutFReqs(List<UserDTO> outFReqs) {
        this.outFReqs = outFReqs;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /*
    @AssertTrue (
        groups = { CreateGroup.class, ModifyGroup.class }
    )
    private boolean checkPwdEquality() {
        boolean rs = this.password == null || this.password.equals(this.confirmPassword);
        //Dev.print("Checking passwords");
        //Dev.printObject(this.password);
        //Dev.printObject(this.confirmPassword);
        //Dev.printObject(rs);
        return rs;
    }
    */

}
