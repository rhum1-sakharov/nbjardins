package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.ConditionDeReglementDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ConditionDeReglement;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ConditionDeReglementMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ConditionDeReglementRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ConditionDeReglementRepoAR extends RepoAR implements ConditionDeReglementRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ConditionDeReglementRepoAR.class);


    @Override
    public String findConditionByEmailArtisan(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<String> query = em.createQuery("SELECT cdr.condition from ConditionDeReglement  cdr " +
                " join cdr.artisanList a " +
                " join a.personne p " +
                " where p.email=:email", String.class);
        query.setParameter("email", email);

        return PersistenceUtils.getSingleResult(query);

    }

    @Override
    public ConditionDeReglementDN findFirst(DataProviderManager dpm) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<ConditionDeReglement> query = em.createQuery("SELECT cdr from ConditionDeReglement  cdr ", ConditionDeReglement.class)
                .setMaxResults(1);

        ConditionDeReglement cdr= PersistenceUtils.getSingleResult(query);

        return ConditionDeReglementMapper.INSTANCE.entityToDomain(cdr);

    }
}
