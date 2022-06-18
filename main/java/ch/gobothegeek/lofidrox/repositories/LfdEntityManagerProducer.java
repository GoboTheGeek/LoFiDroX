package ch.gobothegeek.lofidrox.repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.TransactionScoped;
import java.io.Serializable;

// Produces an entityManager for persistence unit described in persistence.xml file.
// Nothing fancy here. Just keep in mind that persistence unit name is LfdPersistenceUnit
@ApplicationScoped
public class LfdEntityManagerProducer implements Serializable {
    @PersistenceUnit(name = "LfdPersistenceUnit") private EntityManagerFactory entityManagerFactory;

    // return an EntityManager use to access data
    @Produces
    @TransactionScoped
    public EntityManager create() {
        return this.entityManagerFactory.createEntityManager();
    }

    // close current entity manager
    public void close(@Disposes EntityManager em)
    {
        if (em.isOpen()) { em.close(); }
    }

    // getter/setter for EntityManagerFactory
    public EntityManagerFactory getEntityManagerFactory() { return entityManagerFactory; }
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) { this.entityManagerFactory = entityManagerFactory; }
}
