package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.TechnicalException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.banques.ArtisanBanque;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans.banques.ArtisanBanqueMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtisanBanqueRepoAR extends RepoAR implements ArtisanBanqueRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanBanqueRepoAR.class);

    public ArtisanBanqueRepoAR(LocalizeServicePT localizeService) {
        super(localizeService);
    }

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

    @Override
    public List<ArtisanBanqueDN> findAllByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);


        TypedQuery<ArtisanBanque> query = em.createQuery("SELECT ab from ArtisanBanque  ab " +
                " join ab.artisan a " +
                " join a.personne p " +
                " where p.email=:email", ArtisanBanque.class);

        query.setParameter("email", email);

        List<ArtisanBanque> artisanBanqueList = PersistenceUtils.getResultList(query);

        return ArtisanBanqueMapper.INSTANCE.entitiesToDomains(artisanBanqueList);

    }

    @Override
    public ArtisanBanqueDN save(DataProviderManager dpm, ArtisanBanqueDN artisanBanque) throws TechnicalException {
        return (ArtisanBanqueDN) super.save(dpm, artisanBanque);
    }

    @Override
    public Integer removeByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        List<ArtisanBanqueDN> artisanBanqueDNList = findAllByEmail(dpm, email);

        Query query = em.createQuery("DELETE FROM ArtisanBanque ab " +
                " where ab in :abList");
        query.setParameter("abList", ArtisanBanqueMapper.INSTANCE.domainsToEntities(artisanBanqueDNList));

        return query.executeUpdate();

    }
}
