package ports.repositories;

import transactions.DataProviderManager;

import java.math.BigDecimal;

public interface TaxeRepoPT {

    BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email);

}
