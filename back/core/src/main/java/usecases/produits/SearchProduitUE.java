package usecases.produits;

import aop.Transactional;
import domains.produits.ProduitDN;
import exceptions.CleanException;
import models.Precondition;
import models.search.Search;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SearchProduitUE extends AbstractUsecase {

    ProduitRepoPT produitRepo;

    public SearchProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ProduitRepoPT produitRepo) {
        super(ls, transactionManager);
        this.produitRepo = produitRepo;
    }

    @Transactional
    public List<ProduitDN> execute(DataProviderManager dpm, Search search) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "recherche produit"), Objects.nonNull(search))
        );

        return produitRepo.search(dpm, search);
    }
}


