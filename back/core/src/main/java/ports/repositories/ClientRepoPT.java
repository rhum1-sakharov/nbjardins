package ports.repositories;

import domain.models.ClientDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface ClientRepoPT {

    ClientDN saveByIdPersonne(DataProviderManager dpm, String idPersonne);

    String findIdByIdPersonne(DataProviderManager dpm, String idPersonne);

    String findIdByEmail(DataProviderManager dpm,String email) throws PersistenceException;
}
