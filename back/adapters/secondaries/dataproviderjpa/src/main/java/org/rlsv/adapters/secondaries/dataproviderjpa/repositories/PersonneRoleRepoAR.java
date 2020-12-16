package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;
import domain.models.RoleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.ROLES;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.Personne__RoleMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.PersonneRepoPT;
import usecase.ports.repositories.PersonneRoleRepoPT;
import usecase.ports.repositories.RoleRepoPT;

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
    public Personne__RoleDN saveRoleClient(PersonneDN personne) throws domain.exceptions.PersistenceException {
        return savePersonneRole(ROLES.ROLE_CLIENT.getValue(),personne);
    }

    @Override
    public Personne__RoleDN saveRoleArtisan(PersonneDN personne)throws domain.exceptions.PersistenceException {
        return savePersonneRole(ROLES.ROLE_ARTISAN.getValue(),personne);
    }

    private Personne__RoleDN savePersonneRole(String nomRole, PersonneDN personneDN) throws domain.exceptions.PersistenceException {
        try {
            RoleDN roleDN = roleRepo.findByNom(nomRole);
            Role role = RoleMapper.INSTANCE.domainToEntity(roleDN);
            personneDN = personneRepo.findByEmail(personneDN.getEmail());
            Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

            Personne__Role personne__role = new Personne__Role(personne, role);

            Personne__RoleDN personne__roleDN = findByEmailAndRole(personneDN.getEmail(), nomRole);
            if (!Objects.isNull(personne__roleDN)) {
                personne__role.setId(personne__roleDN.getId());
            }

            save(personne__role);

            return Personne__RoleMapper.INSTANCE.entityToDomain(personne__role);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage(), pe);
            throw new domain.exceptions.PersistenceException(pe.getMessage(), pe, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{personneDN.getEmail()});
        }
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


}
