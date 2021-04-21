package usecases.produits;

import aop.Transactional;
import domains.produits.ProduitDN;
import exceptions.CleanException;
import exceptions.TechnicalException;
import helpers.search.filters.SearchFilterHelper;
import keys.produit.ProduitKey;
import models.Precondition;
import models.search.Search;
import models.search.response.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SearchProduitUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SearchProduitUE.class);

    ProduitRepoPT produitRepo;
    SearchFilterHelper<ProduitKey> sfh;

    public SearchProduitUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ProduitRepoPT produitRepo, SearchFilterHelper<ProduitKey> sfh) {
        super(ls, transactionManager);
        this.produitRepo = produitRepo;
        this.sfh = sfh;
    }

    @Transactional
    public SearchResponse<ProduitDN> execute(DataProviderManager dpm, Search search) throws CleanException {

        checkParameters(search);
        sfh.checkSearch(search,ProduitKey.class);

        return produitRepo.search(dpm, search);
    }

    private void checkParameters(Search search) throws TechnicalException {
        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "recherche produit"), Objects.nonNull(search))
        );
    }


}


