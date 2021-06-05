package usecases.personnes;

import annotations.RvlTransactional;
import domains.personnes.PersonneDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByEmailUE extends AbstractUsecase {

    PersonneRepoPT personneRepo;

    public FindByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, PersonneRepoPT personneRepo) {
        super(localizeService, transactionManager);
        this.personneRepo = personneRepo;
    }

    @RvlTransactional
    public PersonneDN execute(DataProviderManager dpm, String email) throws CleanException {

        return personneRepo.findByEmail(dpm,email);
    }
}
