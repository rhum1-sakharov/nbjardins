package ports.repositories.referentiel.taxes;

import domains.referentiel.taxes.TaxeDN;
import transactions.DataProviderManager;

import java.math.BigDecimal;
import java.util.List;

public interface TaxeRepoPT {

    BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email);

    TaxeDN findFirst(DataProviderManager dpm);

    List<TaxeDN> findAll(DataProviderManager dpm);
}
