package usecases.devis;

import aop.Transactional;
import domains.devis.DevisDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class EnregistrerUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public EnregistrerUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public DevisDN execute(DataProviderManager dpm, DevisDN devis) throws CleanException {

        return devisRepo.save(dpm, devis);
    }
}
