package ports.repositories;

import domain.models.DevisDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

public interface DevisRepoPT {

    DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException;

}
