package ports.repositories;

import domains.TaxeDN;
import transactions.DataProviderManager;

import java.math.BigDecimal;

public interface TaxeRepoPT {

    BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email);

    TaxeDN findFirst(DataProviderManager dpm);
}
