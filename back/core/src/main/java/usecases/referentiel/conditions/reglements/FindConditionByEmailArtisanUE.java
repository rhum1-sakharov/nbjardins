package usecases.referentiel.conditions.reglements;

import annotations.RvlTransactional;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindConditionByEmailArtisanUE extends AbstractUsecase {

    ConditionDeReglementRepoPT conditionDeReglementRepo;

    public FindConditionByEmailArtisanUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ConditionDeReglementRepoPT conditionDeReglementRepo) {
        super(localizeService, transactionManager);
        this.conditionDeReglementRepo = conditionDeReglementRepo;
    }

    @RvlTransactional
    public String execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        return conditionDeReglementRepo.findConditionByEmailArtisan(dpm, emailArtisan);

    }
}
