package usecases.referentiel.taxes;

import aop.Transactionnal;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.taxes.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.math.BigDecimal;

public class FindTauxByEmailArtisanUE extends AbstractUsecase {

    TaxeRepoPT taxeRepo;

    public FindTauxByEmailArtisanUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, TaxeRepoPT taxeRepo) {
        super(localizeService, transactionManager);
        this.taxeRepo = taxeRepo;
    }

    @Transactionnal
    public BigDecimal execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        return taxeRepo.findTauxByEmailArtisan(dpm, emailArtisan);

    }
}
