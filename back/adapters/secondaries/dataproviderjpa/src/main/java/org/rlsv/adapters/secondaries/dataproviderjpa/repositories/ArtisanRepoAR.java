package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.models.ArtisanDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ArtisanMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ArtisanRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static localizations.MessageKeys.AUCUN_RESULTAT;

public class ArtisanRepoAR extends RepoAR implements ArtisanRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanRepoAR.class);

    @Override
    public ArtisanDN findByEmail(DataProviderManager dpm, String email) {
        EntityManager em = TransactionManagerAR.getEntityManager(dpm);

        try {
            TypedQuery<Artisan> query = em.createQuery("SELECT a from Artisan  a " +
                    " join a.personne p " +
                    " where p.email=:email", Artisan.class);
            Artisan artisan = query
                    .setParameter("email", email)
                    .getSingleResult();
            return ArtisanMapper.INSTANCE.entityToDomain(artisan);
        } catch (NoResultException nre) {

        }

        return null;
    }

    @Override
    public ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String token) throws PersistenceException {
        try {
            Artisan artisan = null;
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);

            TypedQuery<Artisan> query = em.createQuery("SELECT a from Artisan a " +
                    " join a.application app " +
                    " where app.token=:token", Artisan.class);
            artisan = query.setParameter("token", token).getSingleResult();

            return ArtisanMapper.INSTANCE.entityToDomain(artisan);

        } catch (NoResultException nre) {
            throw new PersistenceException(nre.getMessage(), nre, AUCUN_RESULTAT);
        }
    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) throws PersistenceException {
        String id = null;

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<String> query = em.createQuery("SELECT a.id from Artisan a " +
                    " join a.personne p " +
                    "where p.email=:email", String.class);
            id = query.setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }
        return id;
    }
}
