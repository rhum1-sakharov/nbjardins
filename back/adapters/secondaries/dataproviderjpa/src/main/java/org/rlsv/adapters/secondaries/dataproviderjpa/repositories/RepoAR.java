package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import java.util.Objects;

public abstract class RepoAR<T extends Entity> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoAR.class);

    protected LocalizeServicePT localizeService;

    public RepoAR() {

    }

    private boolean isNew(String id) {
        return Objects.isNull(id);
    }

    public T save(DataProviderManager dpm, T instance) {

        EntityManager em = TransactionManagerAR.getEntityManager(dpm);

        if (isNew(instance.getId())) {
            em.persist(instance);
        } else {
            em.merge(instance);
        }


        return instance;
    }
}
