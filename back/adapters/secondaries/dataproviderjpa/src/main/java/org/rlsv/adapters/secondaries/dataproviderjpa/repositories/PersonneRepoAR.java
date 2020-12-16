package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.PersonneRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public class PersonneRepoAR extends RepoAR implements PersonneRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRepoAR.class);

    @Override
    public PersonneDN findArtisanByApplicationToken(String applicationName) {
        return null;
    }

    @Override
    public PersonneDN findClientByEmail(String email) {
        return null;
    }

    @Override
    public PersonneDN save(PersonneDN personneDN) throws PersistenceException {

        try {

            Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

            Personne personneDb = findByEmail(personneDN.getEmail());

            if (Objects.nonNull(personneDb)) {
                personne.setId(personneDb.getId());
            }

            save(personne);

            return PersonneMapper.INSTANCE.entityToDomain(personne);

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{personneDN.getEmail()});
        }
    }

    @Override
    public PersonneDN saveArtisan(PersonneDN personneDN) {
        return null;
    }


    private Personne findByEmail(String email) {

        Personne personne = null;

        try {
            TypedQuery<Personne> query = em.createQuery("SELECT c from Personne c where c.email=:email", Personne.class);
            personne = query.setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {

        }

        return personne;

    }


}
