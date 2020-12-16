package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.Domain;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;

import javax.persistence.EntityManager;
import java.util.Set;

public abstract class RepoAR<T extends Entity> {

    protected EntityManager em;

    public RepoAR() {
        JpaConfig jpaConfig = JpaConfig.getSingleton();
        em = jpaConfig.getEntityManager();
    }


    public T find(String id) {
        return null;
    }


    public Set<T> findAll() {
        return null;
    }


    public T save(T instance) {

        em.getTransaction().begin();

        if (em.contains(instance)) {
            em.persist(instance);

        } else {
            em.merge(instance);
        }


        em.getTransaction().commit();

        return instance;
    }


    public Set<Domain> savelAll(Set<T> instances) {
        return null;
    }


    public void delete(String id) {

    }


    public void deleteAll() {

    }
}