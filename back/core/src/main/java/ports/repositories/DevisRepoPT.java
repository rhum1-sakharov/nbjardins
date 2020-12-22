package ports.repositories;

import domain.models.DevisDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

import java.util.Date;

public interface DevisRepoPT {

    DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException;

    int countDevisOfMonth(Date date);

}
