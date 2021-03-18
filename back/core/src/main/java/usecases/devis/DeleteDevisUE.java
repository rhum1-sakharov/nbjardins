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

public class DeleteDevisUE extends AbstractUsecase {

    DevisRepoPT devisRepo;


    public DeleteDevisUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public String execute(DataProviderManager dpm, String idDevis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id devis"), Objects.nonNull(idDevis))
        );

        return devisRepo.deleteById(dpm, DevisDN.class, idDevis);
    }
}
