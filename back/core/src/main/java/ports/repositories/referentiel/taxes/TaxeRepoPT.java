package ports.repositories.referentiel.taxes;

import domains.referentiel.taxes.TaxeDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.math.BigDecimal;

public interface TaxeRepoPT  extends RepoPT<TaxeDN> {

    BigDecimal findTauxByEmailArtisan(DataProviderManager dpm, String email);


}
