package ports.repositories;

import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import transactions.DataProviderManager;

import java.util.List;

public interface ConditionDeReglementRepoPT {

    String findConditionByEmailArtisan(DataProviderManager dpm, String email);

    ConditionDeReglementDN findFirst(DataProviderManager dpm);

    List<ConditionDeReglementDN> findAll(DataProviderManager dpm);
}
