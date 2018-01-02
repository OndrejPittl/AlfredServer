package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface PostRepository extends EntityRepository<Post, Long> {



}
