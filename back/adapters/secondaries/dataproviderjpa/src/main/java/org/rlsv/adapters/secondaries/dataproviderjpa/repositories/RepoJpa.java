package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.Domain;
import exceptions.TechnicalException;
import models.Precondition;
import models.search.Search;
import models.search.response.SearchResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.helpers.HelperPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper.MapperUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.JpqlSearchUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static localizations.MessageKeys.LIST_IS_EMPTY;

public class RepoJpa<D extends Domain, E extends Entity> implements RepoPT<D> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoJpa.class);

    protected LocalizeServicePT ls;

    public RepoJpa(LocalizeServicePT ls) {
        this.ls = ls;
    }

    private boolean isNew(String id) {
        return StringUtils.isEmpty(id);
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

    @Override
    public List<String> deleteByIdList(DataProviderManager dpm, Class clazz, List idList) throws TechnicalException {
        Precondition.validate(
                Precondition.init(ls.getMsg(LIST_IS_EMPTY, "idList"), CollectionUtils.isNotEmpty(idList)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "clazz"), Objects.nonNull(clazz)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "dataProviderManager"), Objects.nonNull(dpm))
        );

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        Class<E> entityClass = MapperUtils.mapDomainClassToEntityClass(clazz);

        Query query = em.createQuery("delete from " + entityClass.getSimpleName() +
                " where id in (:idList) ");

        query.setParameter("idList", idList);

        query.executeUpdate();

        em.flush();

        return idList;
    }

    @Override
    public String deleteById(DataProviderManager dpm, Class domainClass, String id) throws TechnicalException {
        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id"), Objects.nonNull(id)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "clazz"), Objects.nonNull(domainClass)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "dataProviderManager"), Objects.nonNull(dpm))
        );

        List<String> idsDeleted = deleteByIdList(dpm, domainClass, Arrays.asList(id));

        Precondition.validate(
                Precondition.init(ls.getMsg(LIST_IS_EMPTY, "ids"), CollectionUtils.isNotEmpty(idsDeleted))
        );


        return idsDeleted.get(0);
    }

    @Override
    public D save(DataProviderManager dpm, D domain) throws TechnicalException {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        E entity = MapperUtils.mapDomainToEntity(domain);
        entity = persistOrMerge(em, entity);
        em.flush();

        return MapperUtils.mapEntityToDomain(entity);
    }

    @Override
    public D findById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);
        Class<E> entityClass = MapperUtils.mapDomainClassToEntityClass(domainClass);
        E instance = em.find(entityClass, id);
        return MapperUtils.mapEntityToDomain(instance);
    }

    @Override
    public List<D> findAll(DataProviderManager dpm, Class<D> domainClass) throws TechnicalException {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        Class<E> entityClass = MapperUtils.mapDomainClassToEntityClass(domainClass);

        TypedQuery<E> query = em.createQuery("SELECT o from " + entityClass.getSimpleName() + "  o ", entityClass);
        List<E> instances = PersistenceUtils.getResultList(query);

        return MapperUtils.mapEntitiesToDomains(instances);
    }

    @Override
    public SearchResponse<D> search(DataProviderManager dpm, Search search, Class<D> domainClass) throws TechnicalException, InstantiationException, IllegalAccessException {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        Class<E> entityClass = MapperUtils.mapDomainClassToEntityClass(domainClass);
        Class<? extends HelperPath> pathClazz = MapperUtils.findPathClassByDomain(domainClass).orElseThrow(() -> new TechnicalException("Unknown path class for domain " + domainClass.getSimpleName()));
        HelperPath pathClassInstance = pathClazz.newInstance();

        String queryBuilt = JpqlSearchUtils.buildSearchQuery(search, pathClassInstance);

        TypedQuery<E> query = em.createQuery(queryBuilt, entityClass);
        query.setFirstResult( JpqlSearchUtils.getFirstResult(search.getPage()));
        query.setMaxResults(search.getPage().getPageSize());
        List<E> entityList = query.getResultList();

        String queryCountBuilt = JpqlSearchUtils.buildCountQuery(search, pathClassInstance);

        TypedQuery<Long> queryCount = em.createQuery(queryCountBuilt, Long.class);
        Long nbElements = queryCount.getSingleResult();

        SearchResponse<D> response = SearchResponse.<D>builder()
                .resultList(MapperUtils.mapEntitiesToDomains(entityList))
                .totalElements(nbElements)
                .build();

        return response;

    }


    public E save(DataProviderManager dpm, E entity) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);
        entity = persistOrMerge(em, entity);
        em.flush();

        return entity;
    }

}
