package ports.repositories.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.List;

public interface ArtisanBanqueRepoPT  extends RepoPT<ArtisanBanqueDN> {

    ArtisanBanqueDN findByEmailAndPrefere(DataProviderManager dpm, String email, boolean prefere);

    List<ArtisanBanqueDN> findAllByEmail(DataProviderManager dpm, String email);

    Integer removeByEmail(DataProviderManager dpm, String email);
}
