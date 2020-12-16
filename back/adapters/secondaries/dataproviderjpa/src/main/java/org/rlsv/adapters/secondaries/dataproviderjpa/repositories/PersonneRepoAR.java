package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import usecase.ports.repositories.PersonneRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class PersonneRepoAR extends RepoAR implements PersonneRepoPT {


    @Override
    public PersonneDN findArtisanByApplicationToken(String applicationName) {
        return null;
    }

    @Override
    public PersonneDN findClientByEmail(String email) {
        return null;
    }

    @Override
    public PersonneDN saveClient(PersonneDN personneDN) {

        Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

        Personne personneDb = findByEmail(personneDN.getEmail());

        if (Objects.nonNull(personneDb)) {
            personne.setId(personneDb.getId());
        }

        save(personne);

        return PersonneMapper.INSTANCE.entityToDomain(personne);
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
        }catch (NoResultException nre){

        }

        return personne;

    }
}
