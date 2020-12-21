package ports.repositories;

import domain.models.RoleDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface RoleRepoPT  {

    RoleDN findByNom(DataProviderManager dpm, String nom) throws PersistenceException;

    String findIdByNom(DataProviderManager dpm,String nom) throws PersistenceException;
}
