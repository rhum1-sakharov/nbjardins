package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import usecase.ports.repositories.ClientRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class ClientRepoAR extends RepoAR implements ClientRepoPT {


    @Override
    public PersonneDN save(PersonneDN personneDN) {

        Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

        Personne personneDb = findByEmail(personneDN.getEmail());

        if (Objects.nonNull(personneDb)) {
            personne.setId(personneDb.getId());
        }

        save(personne);

        return PersonneMapper.INSTANCE.entityToDomain(personne);
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
