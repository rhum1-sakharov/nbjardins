package org.rlsv.adapters.secondaries.dataproviderjpa.utils;

import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersistenceUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceUtils.class);

    /**
     * Récupérer un seul element d'une requete
     *
     * @param query
     * @param <T>
     * @return {@code T} ou null si aucun élément
     */
    public static <T extends Entity> T getSingleResult(Query query) {

        try {
            return (T) query.getSingleResult();
        } catch (NoResultException nre) {
            LOG.warn(nre.getMessage());
            return null;
        }
    }

    /**
     * Récupérer plusieurs elements d'une requete
     *
     * @param query
     * @param <T>
     * @return {@code List<T>} ou liste vide si aucun élément
     */
    public static <T  extends Entity> List<T> getResultList(Query query) {

        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            LOG.warn(nre.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Récuperer l'entitymanager
     * @param dpm
     * @return
     */
    public static EntityManager getEntityManager(DataProviderManager dpm) {

        if(Objects.isNull(dpm) || Objects.isNull(dpm.getManager())){
            throw new PersistenceException("DataProviderManager non defini. Impossible de recuperer l'entityManager");
        }

        return (EntityManager) dpm.getManager();
    }
}
