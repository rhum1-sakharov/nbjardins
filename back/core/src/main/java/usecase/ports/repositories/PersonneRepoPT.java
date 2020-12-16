package usecase.ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;

public interface PersonneRepoPT {

    PersonneDN findArtisanByApplicationToken(String applicationToken) throws PersistenceException;

    PersonneDN save(PersonneDN personneDN) throws PersistenceException;

    PersonneDN findByEmail(String email) throws PersistenceException;


}
