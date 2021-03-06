package ports.repositories.personnes.artisans.options;

import domains.personnes.artisans.options.ArtisanOptionDN;
import transactions.DataProviderManager;

import java.util.List;

public interface  ArtisanOptionRepoPT {

    List<ArtisanOptionDN> findAllByEmail(DataProviderManager dpm, String email);
}
