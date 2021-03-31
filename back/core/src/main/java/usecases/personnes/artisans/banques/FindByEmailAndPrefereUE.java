package usecases.personnes.artisans.banques;

import aop.Transactional;
import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByEmailAndPrefereUE extends AbstractUsecase {

    ArtisanBanqueRepoPT artisanBanqueRepo;

    public FindByEmailAndPrefereUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanBanqueRepoPT artisanBanqueRepo) {
        super(localizeService, transactionManager);
        this.artisanBanqueRepo = artisanBanqueRepo;
    }

    @Transactional
    public ArtisanBanqueDN execute(DataProviderManager dpm, String email, boolean prefere) throws CleanException {

        return artisanBanqueRepo.findByEmailAndPrefere(dpm, email, prefere);
    }
}
