package usecases.produits;

import aop.Transactional;
import domains.produits.ProduitDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class SaveProduitUE extends AbstractUsecase {

    public SaveProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager) {
        super(ls, transactionManager);
    }

    @Transactional
    public ProduitDN execute(DataProviderManager dpm, ProduitDN produit) throws CleanException {

        return null;
    }
}
