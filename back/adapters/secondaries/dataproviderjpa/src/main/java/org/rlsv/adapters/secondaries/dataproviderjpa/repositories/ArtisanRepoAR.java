package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.ArtisanDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ArtisanMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ArtisanRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ArtisanRepoAR extends RepoAR implements ArtisanRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanRepoAR.class);

    @Override
    public ArtisanDN findByEmail(DataProviderManager dpm, String email) {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Artisan> query = em.createQuery("SELECT a from Artisan  a " +
                " join a.personne p " +
                " where p.email=:email", Artisan.class);
        query.setParameter("email", email);

        Artisan artisan = PersistenceUtils.getSingleResult(query);

        return ArtisanMapper.INSTANCE.entityToDomain(artisan);

    }

    @Override
    public ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String token) {

        Artisan artisan = null;
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Artisan> query = em.createQuery("SELECT a from Artisan a " +
                " join a.application app " +
                " where app.token=:token", Artisan.class);
        query.setParameter("token", token);

        artisan = PersistenceUtils.getSingleResult(query);

        return ArtisanMapper.INSTANCE.entityToDomain(artisan);


    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<String> query = em.createQuery("SELECT a.id from Artisan a " +
                " join a.personne p " +
                "where p.email=:email", String.class);
        query.setParameter("email", email)
                .getSingleResult();

        return PersistenceUtils.getSingleResult(query);
    }

    @Override
    public ArtisanDN saveByIdPersonne(DataProviderManager dpm, String id) {
        return null;
    }

    @Override
    public ArtisanDN save(DataProviderManager dpm, ArtisanDN artisan) {

        Artisan artisanEntity= (Artisan) super.save(dpm, ArtisanMapper.INSTANCE.domainToEntity(artisan));

        return ArtisanMapper.INSTANCE.entityToDomain(artisanEntity);

    }
}
