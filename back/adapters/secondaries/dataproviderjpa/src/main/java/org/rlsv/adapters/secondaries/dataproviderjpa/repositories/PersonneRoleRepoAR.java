package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.enums.ROLES;
import domain.models.Personne__RoleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.Personne__RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.repositories.RoleRepoPT;

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
    public Personne__RoleDN saveRoleClient(String idPersonne) throws domain.exceptions.PersistenceException {
        String idRole = roleRepo.findIdByNom(ROLES.ROLE_CLIENT.getValue());
        return savePersonneRole(idRole, idPersonne);
    }

    @Override
    public Personne__RoleDN saveRoleArtisan(String idPersonne) throws domain.exceptions.PersistenceException {
        String idRole = roleRepo.findIdByNom(ROLES.ROLE_ARTISAN.getValue());
        return savePersonneRole(idRole, idPersonne);
    }

    @Override
    public Personne__RoleDN findByEmailAndRole(String email, String nomRole) {

        try {
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
    public Personne__RoleDN findByIdPersonneAndIdRole(String idPersonne, String idRole) {

        try {
            TypedQuery<Personne__Role> query = em.createQuery("SELECT pr from Personne__Role pr " +
                    " join pr.personne p " +
                    " join pr.role r " +
                    "where p.id=:idPersonne and r.nom=:idRole", Personne__Role.class);
            Personne__Role personne__role = query
                    .setParameter("idPersonne", idPersonne)
                    .setParameter("idRole", idRole).getSingleResult();
            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (NoResultException nre) {

        }

        return null;
    }


    private Personne__RoleDN savePersonneRole(String idRole, String idPersonne) throws domain.exceptions.PersistenceException {
        try {

            Role role = new Role();
            role.setId(idRole);

            Personne personne = new Personne();
            personne.setId(idPersonne);

            Personne__Role personne__role = new Personne__Role(personne, role);

            Personne__RoleDN personne__roleDN = findByIdPersonneAndIdRole(idPersonne, idRole);
            if (!Objects.isNull(personne__roleDN)) {
                personne__role.setId(personne__roleDN.getId());
            }

            save(personne__role);

            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage(), pe);
            throw new domain.exceptions.PersistenceException(pe.getMessage(), pe, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{idPersonne});
        }
    }

}
