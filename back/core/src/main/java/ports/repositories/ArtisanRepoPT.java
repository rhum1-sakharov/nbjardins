package ports.repositories;

import domains.models.ArtisanDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface ArtisanRepoPT  {

    ArtisanDN findByEmail(DataProviderManager dpm, String email);

    ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String email) throws PersistenceException;

    String findIdByEmail(DataProviderManager dpm,String email) throws PersistenceException;



}
