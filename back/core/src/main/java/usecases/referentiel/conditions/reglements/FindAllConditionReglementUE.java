package usecases.referentiel.conditions.reglements;

import annotations.Transactional;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;

public class FindAllConditionReglementUE extends AbstractUsecase {

    ConditionDeReglementRepoPT conditionDeReglementRepo;

    public FindAllConditionReglementUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ConditionDeReglementRepoPT conditionDeReglementRepo) {
        super(localizeService, transactionManager);
        this.conditionDeReglementRepo = conditionDeReglementRepo;
    }

    @Transactional
    public List<ConditionDeReglementDN> execute(DataProviderManager dpm) throws CleanException {

        List<ConditionDeReglementDN> conditionDeReglementDNList = conditionDeReglementRepo.findAll(dpm,ConditionDeReglementDN.class);

        return conditionDeReglementDNList;
    }
}
