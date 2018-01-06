package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.Tag;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;

@Repository
public interface TagRepository extends EntityRepository<Tag, Long> {

    @Query(singleResult = SingleResultType.OPTIONAL)
    Tag findFirst1ByNameLike(String name);

    Integer removeById(Long id);

}
