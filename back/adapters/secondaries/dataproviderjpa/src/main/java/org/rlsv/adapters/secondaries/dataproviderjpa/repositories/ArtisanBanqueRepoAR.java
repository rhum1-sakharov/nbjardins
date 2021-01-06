package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.models.ArtisanBanqueDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ArtisanBanque;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ArtisanBanqueMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ArtisanBanqueRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtisanBanqueRepoAR extends RepoAR implements ArtisanBanqueRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanBanqueRepoAR.class);

    @Override
    public List<ArtisanBanqueDN> findByEmailAndPrefere(DataProviderManager dpm, String email, boolean prefere) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);


        TypedQuery<ArtisanBanque> query = em.createQuery("SELECT ab from ArtisanBanque  ab " +
                " join ab.artisan a " +
                " join a.personne p " +
                " where p.email=:email and ab.prefere=:prefere", ArtisanBanque.class);

        query.setParameter("email", email)
                .setParameter("prefere", prefere);

        List<ArtisanBanque> artisanBanqueList = PersistenceUtils.getResultList(query);

        return ArtisanBanqueMapper.INSTANCE.entitiesToDomains(artisanBanqueList);


    }
}
