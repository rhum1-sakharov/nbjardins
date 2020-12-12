package usecase.ports.repositories;

import domain.models.PersonneDN;

public interface ClientRepoPT extends DataServicePT {

    PersonneDN hello();
}
