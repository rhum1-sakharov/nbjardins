package ports.repositories;

import domains.models.PersonneDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface PersonneRepoPT {


    PersonneDN saveClient(DataProviderManager dpm, PersonneDN personneDN) throws PersistenceException;

    PersonneDN findByEmail(DataProviderManager dpm, String email);

    String findIdByEmail(DataProviderManager dpm, String email);


}
