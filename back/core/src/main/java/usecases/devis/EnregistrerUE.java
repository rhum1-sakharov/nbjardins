package usecases.devis;

import aop.Transactionnal;
import domains.DevisDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class EnregistrerUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public EnregistrerUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactionnal
    public DevisDN execute(DataProviderManager dpm, DevisDN devis) throws CleanException {

        return devisRepo.save(dpm, devis);
    }
}
