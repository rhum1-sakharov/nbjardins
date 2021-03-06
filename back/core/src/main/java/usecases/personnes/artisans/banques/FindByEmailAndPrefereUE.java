package usecases.personnes.artisans.banques;

import aop.Transactionnal;
import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;

public class FindByEmailAndPrefereUE extends AbstractUsecase {

    ArtisanBanqueRepoPT artisanBanqueRepo;

    public FindByEmailAndPrefereUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanBanqueRepoPT artisanBanqueRepo) {
        super(localizeService, transactionManager);
        this.artisanBanqueRepo = artisanBanqueRepo;
    }

    @Transactionnal
    public List<ArtisanBanqueDN> execute(DataProviderManager dpm, String email, boolean prefere) throws CleanException {

        return artisanBanqueRepo.findByEmailAndPrefere(dpm, email, prefere);
    }
}
