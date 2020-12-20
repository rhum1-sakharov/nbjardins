package ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.DemandeDeDevisDN;
import domain.transactions.DataProviderManager;

public interface DemandeDeDevisRepoPT  {

    DemandeDeDevisDN save(DataProviderManager dpm, DemandeDeDevisDN demandeDeDevis) throws PersistenceException;

}
