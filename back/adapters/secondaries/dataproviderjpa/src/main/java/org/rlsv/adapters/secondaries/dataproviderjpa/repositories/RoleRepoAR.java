package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.RoleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.RoleRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class RoleRepoAR extends RepoAR implements RoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(RoleRepoAR.class);


    @Override
    public RoleDN findByNom(String nom) throws PersistenceException {

        try {
            TypedQuery<Role> query = em.createQuery("SELECT r from Role r where r.nom=:nom", Role.class);
            Role role = query.setParameter("nom", nom).getSingleResult();
            return RoleMapper.INSTANCE.entityToDomain(role);
        } catch (NoResultException nre) {

        }

        return null;
    }
}
