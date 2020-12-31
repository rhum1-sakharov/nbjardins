package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import enums.ROLES;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.PersonneRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

import static localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public class PersonneRepoAR extends RepoAR implements PersonneRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRepoAR.class);



    @Override
    public PersonneDN saveClient(DataProviderManager dpm, PersonneDN personneDN) throws PersistenceException {

        try {

            Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

            Personne personneDb = findEntityByEmail(dpm, personneDN.getEmail());

            if (Objects.nonNull(personneDb)) {

                Personne__Role prDb = findByEmailAndNomRole(dpm, personneDb.getEmail(), ROLES.ROLE_ARTISAN.getValue());
                if (Objects.nonNull(prDb)) {
                    throw new PersistenceException(String.format("Impossible de modifier l'artisan %s, alors qu'on veut enregistrer un client !", personneDb.getEmail()), null);
                }

                personne.setId(personneDb.getId());
            }

            save(dpm, personne);

            return PersonneMapper.INSTANCE.entityToDomain(personne);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{personneDN.getEmail()});
        }
    }

    @Override
    public PersonneDN findByEmail(DataProviderManager dpm, String email) throws PersistenceException {

        try {
            Personne personne = findEntityByEmail(dpm, email);
            return PersonneMapper.INSTANCE.entityToDomain(personne);
        } catch (NoResultException nre) {

        }

        return null;
    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) throws PersistenceException {
        String id = null;

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<String> query = em.createQuery("SELECT p.id from Personne p " +
                    " where p.email=:email", String.class);
            id = query.setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }
        return id;
    }


    private Personne__Role findByEmailAndNomRole(DataProviderManager dpm, String email, String nomRole) {
        Personne__Role personneRole = null;

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
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

    private Personne findEntityByEmail(DataProviderManager dpm, String email) {

        Personne personne = null;

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);
            TypedQuery<Personne> query = em.createQuery("SELECT c from Personne c where c.email=:email", Personne.class);
            personne = query.setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {

        }

        return personne;

    }


}
