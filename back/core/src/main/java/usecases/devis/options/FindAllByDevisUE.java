package usecases.devis.options;

import annotations.RvlTransactional;
import domains.devis.options.DevisOptionDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindAllByDevisUE extends AbstractUsecase {

    DevisOptionRepoPT devisOptionRepo;

    public FindAllByDevisUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, DevisOptionRepoPT devisOptionRepo) {
        super(ls, transactionManager);
        this.devisOptionRepo = devisOptionRepo;
    }

    @RvlTransactional
    public List<DevisOptionDN> execute(DataProviderManager dpm, String idDevis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED,"id devis"), Objects.nonNull(idDevis))
        );

        return devisOptionRepo.findAllByIdDevis(dpm,idDevis);

    }
}
