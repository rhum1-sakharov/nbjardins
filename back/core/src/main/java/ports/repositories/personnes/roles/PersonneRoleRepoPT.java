package ports.repositories.personnes.roles;

import domains.personnes.roles.Personne__RoleDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface PersonneRoleRepoPT   {


    Personne__RoleDN saveRoleClient(DataProviderManager dpm, String idPersonne) throws PersistenceException;

    Personne__RoleDN saveRoleArtisan(DataProviderManager dpm,String idPersonne) throws PersistenceException;

    Personne__RoleDN findByEmailAndRole(DataProviderManager dpm,String email, String role);

    Personne__RoleDN findByIdPersonneAndIdRole(DataProviderManager dpm,String idPersonne,String idRole);
}
