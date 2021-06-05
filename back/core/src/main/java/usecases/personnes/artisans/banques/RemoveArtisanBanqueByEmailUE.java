package usecases.personnes.artisans.banques;

import annotations.RvlTransactional;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class RemoveArtisanBanqueByEmailUE extends AbstractUsecase {

    ArtisanBanqueRepoPT artisanBanqueRepo;

    public RemoveArtisanBanqueByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanBanqueRepoPT artisanBanqueRepo) {
        super(localizeService, transactionManager);
        this.artisanBanqueRepo = artisanBanqueRepo;
    }

    @RvlTransactional
    public Integer execute(DataProviderManager dpm, String email) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED,"email"), Objects.nonNull(email))
        );


        return artisanBanqueRepo.removeByEmail(dpm, email);
    }
}
