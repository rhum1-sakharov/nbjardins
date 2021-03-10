package ports.repositories.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.TechnicalException;
import transactions.DataProviderManager;

import java.util.List;

public interface ArtisanBanqueRepoPT {

    List<ArtisanBanqueDN> findByEmailAndPrefere(DataProviderManager dpm, String email, boolean prefere);

    List<ArtisanBanqueDN> findAllByEmail(DataProviderManager dpm, String email);

    ArtisanBanqueDN save(DataProviderManager dpm, ArtisanBanqueDN artisanBanque) throws TechnicalException;

    Integer removeByEmail(DataProviderManager dpm, String email);
}
