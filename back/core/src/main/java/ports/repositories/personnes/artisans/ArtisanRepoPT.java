package ports.repositories.personnes.artisans;

import domains.personnes.artisans.ArtisanDN;
import transactions.DataProviderManager;

public interface ArtisanRepoPT  {

    ArtisanDN findByEmail(DataProviderManager dpm, String email);

    ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String email) ;

    String findIdByEmail(DataProviderManager dpm,String email) ;


    ArtisanDN saveByIdPersonne(DataProviderManager dpm, String id);

    ArtisanDN save(DataProviderManager dpm, ArtisanDN artisan);
}
