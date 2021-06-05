package usecases.devis;

import annotations.RvlTransactional;
import domains.devis.DevisDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class SaveDevisUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public SaveDevisUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @RvlTransactional
    public DevisDN execute(DataProviderManager dpm, DevisDN devis) throws CleanException {

        return (DevisDN) devisRepo.save(dpm, devis);
    }
}
