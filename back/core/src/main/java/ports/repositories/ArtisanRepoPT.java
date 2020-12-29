package ports.repositories;

import domain.models.ArtisanDN;
import transactions.DataProviderManager;

public interface ArtisanRepoPT  {

    ArtisanDN findByEmail(DataProviderManager dpm, String email);

}
