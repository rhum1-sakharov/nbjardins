package ports.repositories;

import domain.models.ArtisanBanqueDN;
import transactions.DataProviderManager;

import java.util.List;

public interface ArtisanBanqueRepoPT {

    List<ArtisanBanqueDN> findByEmailAndPrefere(DataProviderManager dpm, String email, boolean prefere);

}
