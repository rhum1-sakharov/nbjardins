package ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.RoleDN;
import domain.transactions.DataProviderManager;

public interface RoleRepoPT  {

    RoleDN findByNom(DataProviderManager dpm, String nom) throws PersistenceException;

    String findIdByNom(DataProviderManager dpm,String nom) throws PersistenceException;
}
