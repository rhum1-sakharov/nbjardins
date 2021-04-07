package usecases.devis;

import aop.Transactional;
import domains.devis.DevisDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindByIdDevisUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public FindByIdDevisUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(ls, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public DevisDN execute(DataProviderManager dpm, String idDevis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id devis"), Objects.nonNull(idDevis))
        );

        return (DevisDN) devisRepo.findById(dpm, DevisDN.class, idDevis);
    }
}
