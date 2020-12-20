package ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.Personne__RoleDN;
import domain.transactions.DataProviderManager;

public interface PersonneRoleRepoPT   {


    Personne__RoleDN saveRoleClient(DataProviderManager dpm, String idPersonne) throws PersistenceException;

    Personne__RoleDN saveRoleArtisan(DataProviderManager dpm,String idPersonne) throws PersistenceException;

    Personne__RoleDN findByEmailAndRole(DataProviderManager dpm,String email, String role);

    Personne__RoleDN findByIdPersonneAndIdRole(DataProviderManager dpm,String idPersonne,String idRole);
}
