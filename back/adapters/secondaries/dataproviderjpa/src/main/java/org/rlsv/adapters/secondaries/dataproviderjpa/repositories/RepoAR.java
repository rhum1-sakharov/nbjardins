package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.Domain;
import exceptions.TechnicalException;
import models.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper.MapperUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static localizations.MessageKeys.LIST_IS_EMPTY;

public  class RepoAR<D extends Domain, E extends Entity> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoAR.class);

    protected LocalizeServicePT localizeService;

    public RepoAR(LocalizeServicePT localizeService) {
        this.localizeService = localizeService;
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

    public String deleteById(DataProviderManager dpm, Class<E> clazz, String id) throws TechnicalException {

        Precondition.validate(
                Precondition.init(localizeService.getMsg(ARG_IS_REQUIRED, "id"), Objects.nonNull(id)),
                Precondition.init(localizeService.getMsg(ARG_IS_REQUIRED, "clazz"), Objects.nonNull(clazz)),
                Precondition.init(localizeService.getMsg(ARG_IS_REQUIRED, "dataProviderManager"), Objects.nonNull(dpm))
        );

        List<String> idsDeleted = deleteByIdList(dpm,clazz, Arrays.asList(id));

        if(CollectionUtils.isEmpty(idsDeleted)){
            throw new TechnicalException(localizeService.getMsg(LIST_IS_EMPTY, "ids"));
        }

        return idsDeleted.get(0);
    }

    public List<String> deleteByIdList(DataProviderManager dpm, Class<E> clazz, List<String> idList) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<E> query = em.createQuery("delete from " + clazz.getSimpleName() +
                " where id in (:idList) ", clazz);

        query.setParameter("idList", idList);

        query.executeUpdate();

        em.flush();

        return idList;
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
