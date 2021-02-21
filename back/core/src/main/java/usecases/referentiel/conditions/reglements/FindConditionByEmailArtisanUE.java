package usecases.referentiel.conditions.reglements;

import aop.Transactionnal;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.ConditionDeReglementRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindConditionByEmailArtisanUE extends AbstractUsecase {

    ConditionDeReglementRepoPT conditionDeReglementRepo;

    public FindConditionByEmailArtisanUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ConditionDeReglementRepoPT conditionDeReglementRepo) {
        super(localizeService, transactionManager);
        this.conditionDeReglementRepo = conditionDeReglementRepo;
    }

    @Transactionnal
    public String execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        return conditionDeReglementRepo.findConditionByEmailArtisan(dpm, emailArtisan);

    }
}
