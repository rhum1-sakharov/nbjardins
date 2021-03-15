package ports.repositories.devis;

import domains.devis.DevisDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

import java.util.Date;
import java.util.List;

public interface DevisRepoPT {

    DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException;

    Long countDevisOfMonth(DataProviderManager dpm,Date date);

    Long existsNumeroDevis(DataProviderManager dpm,String numeroDevis);

    List<DevisDN> findByEmailArtisan(DataProviderManager dpm, String email);
}
