package usecases.referentiel.taxes;

import aop.Transactionnal;
import domains.TaxeDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;

public class FindAllTaxeUE extends AbstractUsecase {

    TaxeRepoPT taxeRepo;

    public FindAllTaxeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, TaxeRepoPT taxeRepo) {
        super(localizeService, transactionManager);
        this.taxeRepo = taxeRepo;
    }

    @Transactionnal
    public List<TaxeDN> execute(TransactionManagerPT tm, DataProviderManager dpm) throws CleanException {

        List<TaxeDN> taxeList = taxeRepo.findAll(dpm);

        return taxeList;
    }
}
