package usecases.personnes.artisans;

import aop.Transactionnal;
import domains.ArtisanDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.ArtisanRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByApplicationTokenUE extends AbstractUsecase {

    ArtisanRepoPT artisanRepo;

    public FindByApplicationTokenUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanRepoPT artisanRepo) {
        super(localizeService, transactionManager);
        this.artisanRepo = artisanRepo;
    }

    @Transactionnal
    public ArtisanDN execute(DataProviderManager dpm, String appToken) throws CleanException {

        return artisanRepo.findArtisanByApplicationToken(dpm, appToken);
    }
}
