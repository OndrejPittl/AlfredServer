package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FRIENDSHIP")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Friendship {

    /**
     * Relation ID. INT, PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;


    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "friendId", nullable = false)
    private User friend;


    @Column(nullable=false, columnDefinition="boolean default false")
    private boolean accepted;


    private Date friendsSince;



    public Friendship() {}

    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.accepted = false;
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

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Date getFriendsSince() {
        return friendsSince;
    }

    public void setFriendsSince(Date friendsSince) {
        this.friendsSince = friendsSince;
    }

    public void approve() {
        this.setAccepted(true);
        this.friendsSince = new Date(System.currentTimeMillis());
    }
}
