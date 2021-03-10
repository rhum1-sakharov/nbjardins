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

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindByEmailUE extends AbstractUsecase {

    ArtisanBanqueRepoPT artisanBanqueRepo;

    public FindByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanBanqueRepoPT artisanBanqueRepo) {
        super(localizeService, transactionManager);
        this.artisanBanqueRepo = artisanBanqueRepo;
    }

    @Transactional
    public List<ArtisanBanqueDN> execute(DataProviderManager dpm, String email) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED,"email"), Objects.nonNull(email))
        );


        return artisanBanqueRepo.findAllByEmail(dpm, email);
    }
}
