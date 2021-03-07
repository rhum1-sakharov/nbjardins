package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.options;

import domains.personnes.artisans.options.ArtisanOptionDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.options.ArtisanOption;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans.options.ArtisanOptionMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtisanOptionRepoAR extends RepoAR implements ArtisanOptionRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanOptionRepoAR.class);

    @Override
    public List<ArtisanOptionDN> findAllByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<ArtisanOption> query = em.createQuery("SELECT ao from ArtisanOption  ao " +
                " join ao.artisan a " +
                " join a.personne p " +
                " where p.email=:email", ArtisanOption.class);

        query.setParameter("email", email);

        List<ArtisanOption> artisanOptionList = PersistenceUtils.getResultList(query);

        return ArtisanOptionMapper.INSTANCE.entitiesToDomains(artisanOptionList);

    }

    @Override
    public ArtisanOptionDN save(DataProviderManager dpm, ArtisanOptionDN artisanOption) {

        ArtisanOption ao = ArtisanOptionMapper.INSTANCE.domainToEntity(artisanOption);

        ao = (ArtisanOption) super.save(dpm,ao);

        return ArtisanOptionMapper.INSTANCE.entityToDomain(ao);
    }
}
