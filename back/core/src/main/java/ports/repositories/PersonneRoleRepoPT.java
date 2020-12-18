package ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.Personne__RoleDN;

public interface PersonneRoleRepoPT {


    Personne__RoleDN saveRoleClient(String idPersonne) throws PersistenceException;

    Personne__RoleDN saveRoleArtisan(String idPersonne) throws PersistenceException;

    Personne__RoleDN findByEmailAndRole(String email, String role);

    Personne__RoleDN findByIdPersonneAndIdRole(String idPersonne,String idRole);
}
