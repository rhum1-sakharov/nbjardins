package ports.repositories;

import domains.ConditionDeReglementDN;
import transactions.DataProviderManager;

public interface ConditionDeReglementRepoPT {

    String findConditionByEmailArtisan(DataProviderManager dpm, String email);

    ConditionDeReglementDN findFirst(DataProviderManager dpm);
}
