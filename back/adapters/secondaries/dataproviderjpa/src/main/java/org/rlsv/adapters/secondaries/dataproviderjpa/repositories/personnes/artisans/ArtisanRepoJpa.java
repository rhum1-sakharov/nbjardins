package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans;

import domains.personnes.artisans.ArtisanDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans.ArtisanMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ArtisanRepoJpa extends RepoJpa<ArtisanDN,Artisan> implements ArtisanRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanRepoJpa.class);

    public ArtisanRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }

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




}
