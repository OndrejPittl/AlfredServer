package cz.ondrejpittl.persistence.repository;

import cz.ondrejpittl.persistence.domain.User;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends EntityRepository<User, Long> {

}
