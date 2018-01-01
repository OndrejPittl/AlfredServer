package cz.ondrejpittl.persistence.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    public EntityManager create() {
        return this.emf.createEntityManager();
    }

    public void close(@Disposes EntityManager m) {
        if(m.isOpen()) m.close();
    }
}
