package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.Domain;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import usecase.ports.repositories.DataServicePT;

import javax.persistence.EntityManager;
import java.util.Set;

public abstract class RepoAR implements DataServicePT {

    protected EntityManager em;

    public RepoAR(){
        JpaConfig jpaConfig = JpaConfig.getSingleton();
        em = jpaConfig.getEntityManager();
    }

    @Override
    public Domain find(String id) {
        return null;
    }

    @Override
    public Set findAll() {
        return null;
    }

    @Override
    public Domain save(Domain instance) {
        return null;
    }

    @Override
    public Domain savelAll(Set instances) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll() {

    }
}
