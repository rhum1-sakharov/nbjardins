package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.TaxeRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

public class TaxeRepoAR extends RepoAR implements TaxeRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeRepoAR.class);

    @Override
    public BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email) {
        EntityManager em = TransactionManagerAR.getEntityManager(dpm);

        try {
            TypedQuery<BigDecimal> query = em.createQuery("SELECT t.taux  from Taxe t " +
                    " join t.artisan__taxeList at " +
                    " join at.artisan a " +
                    " join a.personne p " +
                    " where p.email=:email", BigDecimal.class);
            BigDecimal taux = query
                    .setParameter("email", email)
                    .getSingleResult();
            return taux;
        } catch (NoResultException nre) {

        }

        return BigDecimal.ZERO;
    }
}
