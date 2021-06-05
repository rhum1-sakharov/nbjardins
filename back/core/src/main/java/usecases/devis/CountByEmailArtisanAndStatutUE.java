package usecases.devis;

import annotations.Transactional;
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

public class CountByEmailArtisanAndStatutUE extends AbstractUsecase {

    DevisRepoPT devisRepo;

    public CountByEmailArtisanAndStatutUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(ls, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Transactional
    public Long execute(DataProviderManager dpm, String emailArtisan, STATUT_DEVIS statutDevis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email artisan"), Objects.nonNull(emailArtisan)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "statut devis"), Objects.nonNull(statutDevis))
        );

        return devisRepo.countByEmailArtisanAndStatutDevis(dpm,emailArtisan,statutDevis);
    }
}
