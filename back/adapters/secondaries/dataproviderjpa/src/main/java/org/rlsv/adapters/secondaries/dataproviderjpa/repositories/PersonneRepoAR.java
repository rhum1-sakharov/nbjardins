package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.ROLES;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.PersonneRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

import static domain.localization.MessageKeys.AUCUN_RESULTAT;
import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public class PersonneRepoAR extends RepoAR implements PersonneRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRepoAR.class);

    @Override
    public PersonneDN findArtisanByApplicationToken(String token) throws PersistenceException {

        try {
            Personne artisan = null;

            TypedQuery<Personne> query = em.createQuery("SELECT p from Personne p " +
                    " join p.application app " +
                    " where app.token=:token", Personne.class);
            artisan = query.setParameter("token", token).getSingleResult();

            return PersonneMapper.INSTANCE.entityToDomain(artisan);

        } catch (NoResultException nre) {
            throw new PersistenceException(nre.getMessage(), nre, AUCUN_RESULTAT);
        }

    }


    @Override
    public PersonneDN saveClient(PersonneDN personneDN) throws PersistenceException {

        try {

            Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

            Personne personneDb = findEntityByEmail(personneDN.getEmail());

            if (Objects.nonNull(personneDb)) {

                Personne__Role prDb = findByEmailAndNomRole(personneDb.getEmail(), ROLES.ROLE_ARTISAN.getValue());
                if (Objects.nonNull(prDb)) {
                    throw new PersistenceException(String.format("Impossible de modifier l'artisan %s, alors qu'on veut enregistrer un client !", personneDb.getEmail()), null);
                }

                personne.setId(personneDb.getId());
            }

            save(personne);

            return PersonneMapper.INSTANCE.entityToDomain(personne);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{personneDN.getEmail()});
        }
    }

    @Override
    public PersonneDN findByEmail(String email) throws PersistenceException {

        try {
            Personne personne = findEntityByEmail(email);
            return PersonneMapper.INSTANCE.entityToDomain(personne);
        } catch (NoResultException nre) {

        }

        return null;
    }


    private Personne__Role findByEmailAndNomRole(String email, String nomRole) {
        Personne__Role personneRole = null;

        try {
            TypedQuery<Personne__Role> query = em.createQuery("SELECT pr from Personne__Role pr " +
                    " join pr.personne p " +
                    " join pr.role r " +
                    " where p.email=:email and r.nom=:nomRole", Personne__Role.class);
            personneRole = query.setParameter("email", email)
                    .setParameter("nomRole", nomRole)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }

        return personneRole;
    }

    private Personne findEntityByEmail(String email) {

        Personne personne = null;

        try {
            TypedQuery<Personne> query = em.createQuery("SELECT c from Personne c where c.email=:email", Personne.class);
            personne = query.setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {

        }

        return personne;

    }


}
