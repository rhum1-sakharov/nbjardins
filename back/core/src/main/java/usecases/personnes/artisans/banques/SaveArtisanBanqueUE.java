package usecases.personnes.artisans.banques;

import aop.Transactional;
import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SaveArtisanBanqueUE extends AbstractUsecase {

    ArtisanBanqueRepoPT artisanBanqueRepo;

    public SaveArtisanBanqueUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanBanqueRepoPT artisanBanqueRepo) {
        super(localizeService, transactionManager);
        this.artisanBanqueRepo = artisanBanqueRepo;
    }

    @Transactional
    public ArtisanBanqueDN execute(DataProviderManager dpm, ArtisanBanqueDN artisanBanque) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED,"artisan banque"), Objects.nonNull(artisanBanque))
        );


        return artisanBanqueRepo.save(dpm, artisanBanque);
    }
}
