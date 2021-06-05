package usecases.produits;

import annotations.RvlTransactional;
import domains.produits.ProduitDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SaveProduitUE extends AbstractUsecase {

    ProduitRepoPT produitRepo;

    public SaveProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ProduitRepoPT produitRepo) {
        super(ls, transactionManager);
        this.produitRepo = produitRepo;
    }

    @RvlTransactional
    public ProduitDN execute(DataProviderManager dpm, ProduitDN produit) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "produit"), Objects.nonNull(produit))
        );

        return produitRepo.save(dpm, produit);
    }
}
