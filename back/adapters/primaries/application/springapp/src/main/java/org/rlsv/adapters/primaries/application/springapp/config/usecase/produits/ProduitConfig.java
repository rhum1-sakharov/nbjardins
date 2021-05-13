package org.rlsv.adapters.primaries.application.springapp.config.usecase.produits;

import helpers.search.filters.SearchFilterHelper;
import keys.produit.ProduitKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.produits.SearchProduitUE;


@Configuration
public class ProduitConfig {

    @Bean
    public SearchProduitUE searchProduitUE(LocalizeServicePT ls, TransactionManagerPT tm, ProduitRepoPT produitRepo, SearchFilterHelper<ProduitKey> sfh) {
        return new SearchProduitUE(ls, tm, produitRepo, sfh);
    }


    @Bean
    public ProduitUsecases produitUsecases(SearchProduitUE searchProduitUE) {

        return ProduitUsecases.builder()
                .searchProduitUE(searchProduitUE)
                .build();
    }


}
