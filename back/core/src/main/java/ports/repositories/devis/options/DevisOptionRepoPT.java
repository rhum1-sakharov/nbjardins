package ports.repositories.devis.options;

import domains.devis.options.DevisOptionDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.List;

public interface DevisOptionRepoPT  extends RepoPT<DevisOptionDN> {

    List<DevisOptionDN> findAllByIdDevis(DataProviderManager dpm, String idDevis);
}
