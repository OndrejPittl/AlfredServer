package cz.ondrejpittl.persistence.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String name;

    //@Transient
    @ManyToMany(
        mappedBy = "tags",
        fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Post> posts;



    public Tag() {}

    public Tag(String value) {
        this.name = value;
    }

    public Tag(Long id, String value) {
        this.id = id;
        this.name = value;
    }

    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tag)) return false;
        return ((Tag) obj).getName().equals(this.name);
    }

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
