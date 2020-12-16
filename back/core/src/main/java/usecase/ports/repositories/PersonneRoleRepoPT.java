package usecase.ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;

public interface PersonneRoleRepoPT {

    Personne__RoleDN saveRoleClient(PersonneDN asker) throws PersistenceException;

    Personne__RoleDN saveRoleArtisan(PersonneDN asker) throws PersistenceException;

    Personne__RoleDN findByEmailAndRole(String email, String role);
}
