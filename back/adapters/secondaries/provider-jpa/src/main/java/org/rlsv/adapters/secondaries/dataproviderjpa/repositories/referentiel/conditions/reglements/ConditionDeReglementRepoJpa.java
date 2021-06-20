package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.conditions.reglements;

import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.conditions.reglements.ConditionDeReglement;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ConditionDeReglementRepoJpa extends RepoJpa<ConditionDeReglementDN,ConditionDeReglement>  implements ConditionDeReglementRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ConditionDeReglementRepoJpa.class);

    public ConditionDeReglementRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }


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


}
