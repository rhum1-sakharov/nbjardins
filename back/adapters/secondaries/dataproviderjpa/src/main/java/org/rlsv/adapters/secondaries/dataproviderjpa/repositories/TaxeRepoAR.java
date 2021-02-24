package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.TaxeDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Taxe;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.TaxeMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.TaxeRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class TaxeRepoAR extends RepoAR implements TaxeRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeRepoAR.class);

    @Override
    public BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        try {
            TypedQuery<BigDecimal> query = em.createQuery("SELECT t.taux  from Taxe t " +
                    " join t.artisanList a " +
                    " join a.personne p " +
                    " where p.email=:email", BigDecimal.class);
            BigDecimal taux = query
                    .setParameter("email", email)
                    .getSingleResult();
            return taux;
        } catch (NoResultException nre) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public TaxeDN findFirst(DataProviderManager dpm) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Taxe> query = em.createQuery("SELECT t from Taxe  t ", Taxe.class)
                .setMaxResults(1);

        Taxe taxe = PersistenceUtils.getSingleResult(query);

        return TaxeMapper.INSTANCE.entityToDomain(taxe);

    }

    @Override
    public List<TaxeDN> findAll(DataProviderManager dpm) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Taxe> query = em.createQuery("SELECT t from Taxe  t order by t.taux", Taxe.class);

        List<Taxe> taxeList = PersistenceUtils.getResultList(query);

        return TaxeMapper.INSTANCE.entitiesToDomains(taxeList);

    }
}
