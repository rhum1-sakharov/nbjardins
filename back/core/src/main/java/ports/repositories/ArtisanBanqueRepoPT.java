package ports.repositories;

import domains.ArtisanBanqueDN;
import transactions.DataProviderManager;

import java.util.List;

public interface ArtisanBanqueRepoPT {

    List<ArtisanBanqueDN> findByEmailAndPrefere(DataProviderManager dpm, String email, boolean prefere);

}
