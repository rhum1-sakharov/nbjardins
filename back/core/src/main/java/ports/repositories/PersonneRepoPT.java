package ports.repositories;

import domain.models.PersonneDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface PersonneRepoPT  {

    PersonneDN findArtisanByApplicationToken(DataProviderManager dpm, String applicationToken) throws PersistenceException;

    PersonneDN saveClient(DataProviderManager dpm,PersonneDN personneDN) throws PersistenceException;

    PersonneDN findByEmail(DataProviderManager dpm,String email) throws PersistenceException;

    String findIdByEmail(DataProviderManager dpm,String email) throws PersistenceException;


}
