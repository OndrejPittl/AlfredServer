package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.persistence.domain.Post;

import javax.persistence.*;
import java.util.List;


public class TagDTO {

    private Long id;

    private String name;

    //private List<PostDTO> posts;


    public TagDTO() {}

    public TagDTO(Long id, String value) {
        this.id = id;
        this.name = value;
    }

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

    /*
    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
    */
}
