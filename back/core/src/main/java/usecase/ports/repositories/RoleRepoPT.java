package usecase.ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.RoleDN;

public interface RoleRepoPT {

    RoleDN findByNom(String nom) throws PersistenceException;

}
