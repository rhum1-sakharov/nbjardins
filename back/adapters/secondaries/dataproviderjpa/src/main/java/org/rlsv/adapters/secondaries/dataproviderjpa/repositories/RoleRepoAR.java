package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.RoleDN;
import domain.transactions.DataProviderManager;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.RoleMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.RoleRepoPT;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class RoleRepoAR extends RepoAR implements RoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(RoleRepoAR.class);


    @Override
    public RoleDN findByNom(DataProviderManager dpm,String nom) throws PersistenceException {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<Role> query = em.createQuery("SELECT r from Role r where r.nom=:nom", Role.class);
            Role role = query.setParameter("nom", nom).getSingleResult();
            return RoleMapper.INSTANCE.entityToDomain(role);
        } catch (NoResultException nre) {

        }

        return null;
    }

    @Override
    public String findIdByNom(DataProviderManager dpm,String nom) throws PersistenceException {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<String> query = em.createQuery("SELECT r.id from Role r where r.nom=:nom", String.class);
            String idRole = query.setParameter("nom", nom).getSingleResult();
            return idRole;
        } catch (NoResultException nre) {

        }

        return null;
    }
}
