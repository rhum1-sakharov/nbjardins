package usecases.referentiel.taxes;

import annotations.Transactional;
import domains.referentiel.taxes.TaxeDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.taxes.TaxeRepoPT;
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

    @Transactional
    public List<TaxeDN> execute(DataProviderManager dpm) throws CleanException {

        List<TaxeDN> taxeList = taxeRepo.findAll(dpm, TaxeDN.class);

        return taxeList;
    }
}
