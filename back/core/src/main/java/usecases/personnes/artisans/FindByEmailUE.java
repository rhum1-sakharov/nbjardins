package usecases.personnes.artisans;

import aop.Transactional;
import domains.personnes.artisans.ArtisanDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByEmailUE extends AbstractUsecase {

    ArtisanRepoPT artisanRepo;

    public FindByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanRepoPT artisanRepo) {
        super(localizeService, transactionManager);
        this.artisanRepo = artisanRepo;
    }

    @Transactional
    public ArtisanDN execute(DataProviderManager dpm, String email) throws CleanException {

        return artisanRepo.findByEmail(dpm,email);
    }
}
