package ports.repositories.personnes.artisans;

import domains.personnes.artisans.ArtisanDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

public interface ArtisanRepoPT  extends RepoPT<ArtisanDN> {

    ArtisanDN findByEmail(DataProviderManager dpm, String email);

    ArtisanDN findArtisanByApplicationToken(DataProviderManager dpm, String email) ;

}
