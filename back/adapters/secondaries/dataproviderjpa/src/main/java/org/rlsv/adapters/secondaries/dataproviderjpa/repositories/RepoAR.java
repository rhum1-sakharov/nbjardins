package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.Domain;
import exceptions.TechnicalException;
import org.apache.commons.lang3.StringUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper.MapperUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;

public abstract class RepoAR<D extends Domain, E extends Entity> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoAR.class);

    protected LocalizeServicePT localizeService;

    public RepoAR() {

    }

    private boolean isNew(String id) {
        return StringUtils.isEmpty(id);
    }

    public D save(DataProviderManager dpm, D domain) throws TechnicalException {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        E entity = MapperUtils.mapDomainToEntity(domain);
        entity = persistOrMerge(em, entity);
        em.flush();

        return MapperUtils.mapEntityToDomain(entity);
    }

    public E save(DataProviderManager dpm, E entity) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);
        entity = persistOrMerge(em, entity);
        em.flush();

        return entity;
    }

    private E persistOrMerge(EntityManager em, E entity) {

        if (isNew(entity.getId())) {
            entity.setId(null);
            em.persist(entity);
        } else {
            em.merge(entity);
        }

        return entity;

    }
}
