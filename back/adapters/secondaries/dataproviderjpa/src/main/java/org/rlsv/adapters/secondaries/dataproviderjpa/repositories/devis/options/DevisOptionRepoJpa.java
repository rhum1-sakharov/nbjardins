package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis.options;

import domains.devis.options.DevisOptionDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.options.DevisOption;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.devis.options.DevisOptionMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DevisOptionRepoJpa extends RepoJpa<DevisOptionDN,DevisOption>  implements DevisOptionRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DevisOptionRepoJpa.class);

    public DevisOptionRepoJpa(LocalizeServicePT ls) {
        super(ls);
    }

    @Override
    public List<DevisOptionDN> findAllByIdDevis(DataProviderManager dpm, String idDevis) {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<DevisOption> query = em.createQuery("SELECT do from DevisOption do " +
                " join do.devis d " +
                "where d.id=:idDevis ", DevisOption.class);
        query.setParameter("idDevis", idDevis);

        List<DevisOption> devisOptionList = PersistenceUtils.getResultList(query);

        return DevisOptionMapper.INSTANCE.entitiesToDomains(devisOptionList);
    }
}
