package ports.repositories;

import domains.ArtisanDN;
import transactions.DataProviderManager;

public interface ArtisanRepoPT  {

    ArtisanDN findByEmail(DataProviderManager dpm, String email);

    ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String email) ;

    String findIdByEmail(DataProviderManager dpm,String email) ;



}
