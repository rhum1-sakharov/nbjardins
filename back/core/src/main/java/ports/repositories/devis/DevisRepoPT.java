package ports.repositories.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

import java.util.Date;
import java.util.List;

public interface DevisRepoPT {

    DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException;

    Long countDevisOfMonth(DataProviderManager dpm,Date date);

    Long existsNumeroDevis(DataProviderManager dpm,String numeroDevis);

    List<DevisDN> findByEmailArtisan(DataProviderManager dpm, String email);

    Integer deleteById(DataProviderManager dpm, String idDevis);

    DevisDN changeStatus(DataProviderManager dpm, String idDevis, STATUT_DEVIS statutDevis);
}
