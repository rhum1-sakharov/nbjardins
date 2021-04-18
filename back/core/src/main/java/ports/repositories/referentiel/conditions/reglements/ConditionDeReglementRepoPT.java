package ports.repositories.referentiel.conditions.reglements;

import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

public interface ConditionDeReglementRepoPT  extends RepoPT<ConditionDeReglementDN> {

    String findConditionByEmailArtisan(DataProviderManager dpm, String email);

}
