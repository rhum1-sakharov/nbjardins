package usecases.personnes;

import aop.Transactionnal;
import domains.PersonneDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByEmailUE extends AbstractUsecase {

    PersonneRepoPT personneRepo;

    public FindByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, PersonneRepoPT personneRepo) {
        super(localizeService, transactionManager);
        this.personneRepo = personneRepo;
    }

    @Transactionnal
    public PersonneDN execute(DataProviderManager dpm, String email) throws CleanException {

        return personneRepo.findByEmail(dpm,email);
    }
}