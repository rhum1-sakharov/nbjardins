package ports.repositories.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.Date;
import java.util.List;

public interface DevisRepoPT extends RepoPT {

    Long countDevisOfMonth(DataProviderManager dpm,Date date);

    Long existsNumeroDevis(DataProviderManager dpm,String numeroDevis);

    List<DevisDN> findByEmailArtisan(DataProviderManager dpm, String email);

    DevisDN changeStatus(DataProviderManager dpm, String idDevis, STATUT_DEVIS statutDevis);

    Long countByEmailArtisanAndStatutDevis(DataProviderManager dpm, String emailArtisan, STATUT_DEVIS statutDevis);

    List<DevisDN> findByEmailArtisanAndStatutWithOrder(DataProviderManager dpm, String emailArtisan, STATUT_DEVIS statutDevis);

}
