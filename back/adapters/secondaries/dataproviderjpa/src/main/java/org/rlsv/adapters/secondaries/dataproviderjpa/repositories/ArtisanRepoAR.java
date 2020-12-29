package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.ArtisanDN;
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
}
