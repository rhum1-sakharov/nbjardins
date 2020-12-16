package usecase.ports.repositories;

import domain.models.PersonneDN;

public interface PersonneRepoPT {

    PersonneDN findArtisanByApplicationToken(String applicationToken);

    PersonneDN findClientByEmail(String email);

    PersonneDN saveClient(PersonneDN personneDN);

    PersonneDN saveArtisan(PersonneDN personneDN);


}
