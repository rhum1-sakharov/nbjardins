package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.RoleDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.RoleMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.RoleRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class RoleRepoAR extends RepoAR implements RoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(RoleRepoAR.class);


    @Override
    public RoleDN findByNom(DataProviderManager dpm, String nom) throws PersistenceException {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Role> query = em.createQuery("SELECT r from Role r where r.nom=:nom", Role.class);
        query.setParameter("nom", nom);

        Role role = PersistenceUtils.getSingleResult(query);

        return RoleMapper.INSTANCE.entityToDomain(role);

    }

    @Override
    public String findIdByNom(DataProviderManager dpm, String nom) throws PersistenceException {

            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            TypedQuery<String> query = em.createQuery("SELECT r.id from Role r where r.nom=:nom", String.class);
            query.setParameter("nom", nom);

            return PersistenceUtils.getSingleResult(query);
    }
}
