package ports.repositories;

import transactions.DataProviderManager;

public interface ConditionDeReglementRepoPT {

    String findConditionByEmailArtisan(DataProviderManager dpm, String email);

}
