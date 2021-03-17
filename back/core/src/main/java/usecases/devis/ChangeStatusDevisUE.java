package usecases.devis;

import aop.Transactional;
import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class ChangeStatusDevisUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public ChangeStatusDevisUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public DevisDN execute(DataProviderManager dpm, String idDevis, STATUT_DEVIS statutDevis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id devis"), Objects.nonNull(idDevis)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "statut devis"), Objects.nonNull(statutDevis))
        );

        return devisRepo.changeStatus(dpm, idDevis, statutDevis);
    }
}
