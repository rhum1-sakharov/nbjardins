package usecase.ports.repositories;

import domain.models.PersonneDN;

public interface ClientRepoPT {

    PersonneDN save(PersonneDN personneDN);

}
