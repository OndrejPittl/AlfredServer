package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Tag;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface TagRepository extends EntityRepository<Tag, Long> {

}
