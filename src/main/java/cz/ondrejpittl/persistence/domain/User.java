package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.ondrejpittl.dev.Dev;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.*;

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
    @Column(nullable = false, length = 50)
    private String firstName;

    /**
     * Last name. VARCHAR(255)
     */
    @Column(nullable = false, length = 50)
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


    private Date birth;



    /**
     * Password.
     */
    @Column(length = 60)
    private String password;

    /**
     * Disabling user.
     */
    @Column(nullable=false, columnDefinition="boolean default false")
    private boolean disabled;



    /**
     * Password confirmation.
     */
    @Transient
    private String confirmPassword;


    @Transient
    private String token;



    // --- posts ---

    /**
     * User's posts.
     */
    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        cascade = { CascadeType.MERGE }
    )
    @OrderBy("date DESC")
    private Set<Post> posts = new HashSet<Post>();



    // --- comments ---

    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @OrderBy("date")
    private Set<Comment> comments = new HashSet<>();



    // --- friendship ---

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private Set<Friendship> friendWith = new HashSet<>();

    @OneToMany(
        mappedBy = "friend",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private Set<Friendship> friendedBy = new HashSet<>();


    // --- ratings ---

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @OrderBy("date ASC")
    private Set<Rating> rated = new HashSet<>();

    /*
    @ManyToMany(
        mappedBy = "rating",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.EAGER
    )
    private Set<Post> rated = new HashSet<>();
    */


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

    public Set<Friendship> getFriendWith() {
        return friendWith;
    }

    public void setFriendWith(Set<Friendship> friendWith) {
        this.friendWith = friendWith;
    }

    public Set<Friendship> getFriendedBy() {
        return friendedBy;
    }

    public void setFriendedBy(Set<Friendship> friendedBy) {
        this.friendedBy = friendedBy;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /*
    public Set<Post> getRated() {
        return rated;
    }

    public void setRated(Set<Post> rated) {
        this.rated = rated;
    }
    */

    public Set<Rating> getRated() {
        return rated;
    }

    public void setRated(Set<Rating> rated) {
        this.rated = rated;
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

    public void addFriend(User friend) {
        Friendship f = new Friendship(this, friend);
        this.friendWith.add(f);
        //this.friendedBy.add(f);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        return ((User) obj).getId().equals(this.id);
    }


    public boolean approveFriendRequest(Long friendId) {
        Friendship friendship = null;

        // approve only request sent from another user!
        //      –> this.friendedBy
        //      –> f.getUser() = request initiator –> f.getUser() == friendId
        for(Friendship f : this.friendedBy) {
            if(f.getUser().getId().equals(friendId) && !f.getUser().isDisabled()) {
                //Dev.print("Found friend request from " + f.getUser().getEmail() + " ID " + f.getUser().getId() + ", looking for ID " + friendId);
                friendship = f;
                break;
            }
        }

        if(friendship == null) {
            return false;
        }

        //Dev.print("Approving friend request " + friendship.getUser().getEmail() + " –> " + friendship.getFriend().getEmail());
        friendship.approve();
        return true;
    }

    public Friendship cancelFriendRequest(Long friendId) {
        // cancel any friend request / friendship
        //      –> this.friendWith + f.getFriend()
        //      –> this.friendedBy + f.getUser()
        //          == no matter who initiated the relation

        Friendship friendship = null;

        // incoming friend requests
        for(Friendship f : this.friendedBy) {
            if(f.getUser().getId().equals(friendId)) {
                //Dev.print("Found incoming friend request from " + f.getUser().getEmail() + " ID " + f.getUser().getId() + ", looking for ID " + friendId);
                friendship = f;
                //this.friendedBy.remove(friendship);
                break;
            }
        }

        // outcoming friend requests
        if(friendship == null) {
            for(Friendship f : this.friendWith) {
                if(f.getFriend().getId().equals(friendId)) {
                    //Dev.print("Found outcoming friend request from " + f.getFriend().getEmail() + " ID " + f.getFriend().getId() + ", looking for ID " + friendId);
                    friendship = f;
                    //this.friendWith.remove(friendship);
                    break;
                }
            }
        }

        if(friendship != null) {
            //Dev.print("Found friendship: " + friendship.getUser().getEmail() + " –> " + friendship.getFriend().getEmail());
        }

        return friendship;
    }

    private Friendship findFriendship(Long friendId) {
        for(Friendship f : this.friendWith) {
            if(f.getFriend().getId().equals(friendId)) {
                return f;
            }
        }
        return null;
    }

    public List<User> getFriends() {
        List<User> friends = new ArrayList<>();

        for (Friendship f : this.friendedBy) {
            if(f.isAccepted()) friends.add(f.getUser());
        }

        for (Friendship f : this.friendWith) {
            if(f.isAccepted()) friends.add(f.getFriend());
        }

        return friends;
    }

    public List<User> getIncomingFriendRequests() {
        List<User> reqs = new ArrayList<>();
        for (Friendship f : this.friendedBy) {
            if(!f.isAccepted()) reqs.add(f.getUser());
        }
        return reqs;
    }

    public List<User> getOutcomingFriendRequests() {
        List<User> reqs = new ArrayList<>();
        for (Friendship f : this.friendWith) {
            if(!f.isAccepted()) reqs.add(f.getFriend());
        }
        return reqs;
    }

}
