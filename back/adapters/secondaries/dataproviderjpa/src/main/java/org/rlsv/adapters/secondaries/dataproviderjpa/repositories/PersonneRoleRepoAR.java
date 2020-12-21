package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.enums.ROLES;
import domain.models.Personne__RoleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.Personne__RoleMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.repositories.RoleRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Objects;

import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public class PersonneRoleRepoAR extends RepoAR implements PersonneRoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRoleRepoAR.class);

    PersonneRepoPT personneRepo;
    RoleRepoPT roleRepo;


    public PersonneRoleRepoAR(PersonneRepoPT personneRepo, RoleRepoPT roleRepo) {
        super();
        this.personneRepo = personneRepo;
        this.roleRepo = roleRepo;
    }


    @Override
    public Personne__RoleDN saveRoleClient(DataProviderManager dpm,String idPersonne) throws exceptions.PersistenceException {
        String idRole = roleRepo.findIdByNom(dpm,ROLES.ROLE_CLIENT.getValue());
        return savePersonneRole(dpm,idRole, idPersonne);
    }

    @Override
    public Personne__RoleDN saveRoleArtisan(DataProviderManager dpm,String idPersonne) throws exceptions.PersistenceException {
        String idRole = roleRepo.findIdByNom(dpm,ROLES.ROLE_ARTISAN.getValue());
        return savePersonneRole(dpm,idRole, idPersonne);
    }

    @Override
    public Personne__RoleDN findByEmailAndRole(DataProviderManager dpm,String email, String nomRole) {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<Personne__Role> query = em.createQuery("SELECT pr from Personne__Role pr " +
                    " join pr.personne p " +
                    " join pr.role r " +
                    "where p.email=:email and r.nom=:nomRole", Personne__Role.class);
            Personne__Role personne__role = query
                    .setParameter("email", email)
                    .setParameter("nomRole", nomRole).getSingleResult();
            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (NoResultException nre) {

        }

        return null;
    }

    @Override
    public Personne__RoleDN findByIdPersonneAndIdRole(DataProviderManager dpm,String idPersonne, String idRole) {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<Personne__Role> query = em.createQuery("SELECT pr from Personne__Role pr " +
                    " join pr.personne p " +
                    " join pr.role r " +
                    "where p.id=:idPersonne and r.id=:idRole", Personne__Role.class);
            Personne__Role personne__role = query
                    .setParameter("idPersonne", idPersonne)
                    .setParameter("idRole", idRole).getSingleResult();
            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (NoResultException nre) {

        }

        return null;
    }


    private Personne__RoleDN savePersonneRole(DataProviderManager dpm,String idRole, String idPersonne) throws exceptions.PersistenceException {
        try {

            Role role = new Role();
            role.setId(idRole);

            Personne personne = new Personne();
            personne.setId(idPersonne);

            Personne__Role personne__role = new Personne__Role(personne, role);

            Personne__RoleDN personne__roleDN = findByIdPersonneAndIdRole(dpm,idPersonne, idRole);
            if (!Objects.isNull(personne__roleDN)) {
                personne__role.setId(personne__roleDN.getId());
            }

            save(dpm,personne__role);

            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage(), pe);
            throw new exceptions.PersistenceException(pe.getMessage(), pe, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{idPersonne});
        }
    }

}
