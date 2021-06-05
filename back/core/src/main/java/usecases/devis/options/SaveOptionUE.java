package usecases.devis.options;

import annotations.RvlTransactional;
import domains.devis.options.DevisOptionDN;
import exceptions.CleanException;
import models.Precondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SaveOptionUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SaveOptionUE.class);

    DevisOptionRepoPT devisOptionRepo;

    public SaveOptionUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, DevisOptionRepoPT devisOptionRepo) {
        super(ls, transactionManager);
        this.devisOptionRepo = devisOptionRepo;
    }

    @RvlTransactional
    public DevisOptionDN execute(DataProviderManager dpm, DevisOptionDN devisOption) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "devis option"), Objects.nonNull(devisOption))
        );

        return (DevisOptionDN) devisOptionRepo.save(dpm, devisOption);
    }


}
