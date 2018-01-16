package cz.ondrejpittl.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TAGS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable=false)
    private String name;

    //@Transient
    @ManyToMany(
        mappedBy = "tags",
        fetch = FetchType.EAGER
        //cascade = {CascadeType.MERGE}
        //cascade = {CascadeType.PERSIST}
    )
    private Set<Post> posts = new HashSet<>();



    public Tag() {}

    public Tag(String value) {
        this.name = value;
    }

    public Tag(Long id, String value) {
        this.id = id;
        this.name = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tag)) return false;
        return ((Tag) obj).getName().equals(this.name);
    }

    /*
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
