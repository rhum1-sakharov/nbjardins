package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import org.apache.commons.lang3.StringUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;

public abstract class RepoAR<T extends Entity> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoAR.class);

    protected LocalizeServicePT localizeService;

    public RepoAR() {

    }

    private boolean isNew(String id) {
        return StringUtils.isEmpty(id);
    }

    public T save(DataProviderManager dpm, T instance) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        if (isNew(instance.getId())) {
            instance.setId(null);
            em.persist(instance);
        } else {
            em.merge(instance);
        }

        em.flush();

        return instance;
    }
}
