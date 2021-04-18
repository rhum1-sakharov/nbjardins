package usecases.produits;

import aop.Transactional;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class RemoveProduitUE extends AbstractUsecase {


    public RemoveProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager) {
        super(ls, transactionManager);
    }

    @Transactional
    public String execute(DataProviderManager dpm, String idProduit) throws CleanException {

        return null;
    }
}
