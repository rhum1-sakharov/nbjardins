package usecase.ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;

public interface PersonneRepoPT {

    PersonneDN findArtisanByApplicationToken(String applicationToken);

    PersonneDN findClientByEmail(String email);

    PersonneDN save(PersonneDN personneDN) throws PersistenceException;

    PersonneDN saveArtisan(PersonneDN personneDN);


}
