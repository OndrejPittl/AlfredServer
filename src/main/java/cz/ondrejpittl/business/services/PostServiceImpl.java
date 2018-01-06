package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.utils.Encryptor;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    @Inject
    PostRepository postRepository;

    @Inject
    PostRestMapper postMapper;

    @Inject
    TagService tagService;


    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    public Post getPost(Long id) {
        return this.postRepository.findBy(id);
    }

    @Transactional
    public Post createPost(PostDTO dto) {
        Post p = postMapper.fromDTO(dto);
        return postRepository.save(p);
    }

    @Transactional
    public Post removePost(Long id) {
        Dev.print("POST DELETE: Looking for Post ID " + id);
        Set<Tag> tags = this.postRepository.findBy(id).getTags();
        Dev.print("POST DELETE: Removing Post ID " + id);

        this.postRepository.removeById(id);
        this.tagService.removeOrphans(tags);
        //this.tagService.removeOrphans();
        return null;
    }

    @Transactional
    public Post modifyPost(Long id, PostDTO dto) {
        boolean modified = false;
        Post p = this.postMapper.fromDTO(dto);
        Set<Tag> prevTags = null;

        Dev.print("POST PUT: Looking fot Post ID " + id);
        Post orig = this.getPost(id);
        Dev.print("POST PUT: Modifying Post ID " + id);


        if(orig == null) {
            return null;
        }

        if(!p.getTitle().equals(orig.getTitle())) {
            orig.setTitle(p.getTitle());
            modified = true;
        }

        if(!p.getBody().equals(orig.getBody())) {
            orig.setBody(p.getBody());
            modified = true;
        }

        if(!p.getImage().equals(orig.getImage())) {
            orig.setImage(p.getImage());
            modified = true;
        }

        if(p.getTags() != null) {
            prevTags = new HashSet<>(orig.getTags());

            orig.setTags(new HashSet<Tag>() {{
                Iterator<Tag> it = p.getTags().iterator();
                while(it.hasNext()) {
                    Tag t = tagService.getOrCreateTag(it.next().getName());
                    add(t);
                }
            }});

            modified = true;
        }


        if(modified) {
            // modification –> update date
            orig.setLastModified(new Date(System.currentTimeMillis()));
        }

        this.postRepository.saveAndFlush(orig);


        if(prevTags != null) {
            this.tagService.removeOrphans(prevTags);
        }



        return orig;
    }
}
