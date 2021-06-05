package usecases.produits;

import annotations.Transactional;
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

public class RemoveProduitUE extends AbstractUsecase {

    ProduitRepoPT produitRepo;

    public RemoveProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ProduitRepoPT produitRepo) {
        super(ls, transactionManager);
        this.produitRepo = produitRepo;
    }

    @Transactional
    public String execute(DataProviderManager dpm, String idProduit) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "id produit"), Objects.nonNull(idProduit))
        );

        return produitRepo.deleteById(dpm, ProduitDN.class, idProduit);
    }
}


