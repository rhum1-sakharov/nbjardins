package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.roles;

import domains.personnes.PersonneDN;
import domains.referentiel.roles.RoleDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.roles.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.referentiel.roles.RoleMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.roles.RoleRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoleRepoJpa extends RepoJpa<RoleDN,Role>  implements RoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(RoleRepoJpa.class);

    public RoleRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }



    @Override
    public String findIdByNom(DataProviderManager dpm, String nom) throws PersistenceException {

            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            TypedQuery<String> query = em.createQuery("SELECT r.id from Role r where r.nom=:nom", String.class);
            query.setParameter("nom", nom);

            return PersistenceUtils.getSingleResult(query);
    }

    @Override
    public List<RoleDN> findByPersonne(DataProviderManager dpm, PersonneDN personne) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Role> query = em.createQuery("SELECT distinct(r) from Role r " +
                " join r.personne__roleList pr " +
                " join pr.personne p " +
                " where p.email=:email", Role.class);
        query.setParameter("email", personne.getEmail());

        List<Role> roleList = PersistenceUtils.getResultList(query);

        return RoleMapper.INSTANCE.entitiesToDomains(roleList);

    }
}
