package ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import domain.transactions.DataProviderManager;

public interface PersonneRepoPT  {

    PersonneDN findArtisanByApplicationToken(DataProviderManager dpm, String applicationToken) throws PersistenceException;

    PersonneDN saveClient(DataProviderManager dpm,PersonneDN personneDN) throws PersistenceException;

    PersonneDN findByEmail(DataProviderManager dpm,String email) throws PersistenceException;

    String findIdByEmail(DataProviderManager dpm,String email) throws PersistenceException;


}
