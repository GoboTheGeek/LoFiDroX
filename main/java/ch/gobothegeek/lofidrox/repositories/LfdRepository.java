package ch.gobothegeek.lofidrox.repositories;

import org.apache.deltaspike.data.api.EntityManagerDelegate;
import org.apache.deltaspike.data.api.EntityPersistenceRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import java.io.Serializable;

// describe a generice repository.
// Please note that this repository is NOT annotated with @Repository (otherwise it will be used for queries, leading to errors)
public abstract class LfdRepository<E, P extends Serializable> implements EntityPersistenceRepository<E, P>, EntityManagerDelegate<E>, CriteriaSupport<E> { }
