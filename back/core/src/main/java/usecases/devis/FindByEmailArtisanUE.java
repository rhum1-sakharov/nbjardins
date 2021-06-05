package usecases.devis;

import annotations.Transactional;
import domains.devis.DevisDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindByEmailArtisanUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public FindByEmailArtisanUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(ls, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public List<DevisDN> execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email"), Objects.nonNull(emailArtisan))
        );

        return devisRepo.findByEmailArtisan(dpm,emailArtisan);
    }
}
