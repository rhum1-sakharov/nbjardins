package org.rlsv.adapters.primaries.application.springapp.config.helpers;

import helpers.search.filters.SearchFilterHelper;
import keys.client.ClientKey;
import keys.produit.ProduitKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;


@Configuration
public class HelpersConfig {

    @Bean
    public SearchFilterHelper<ClientKey> clientSearchFilterHelper(LocalizeServicePT ls) {
        return new SearchFilterHelper<ClientKey>(ls);
    }

    @Bean
    public SearchFilterHelper<ProduitKey> produitSearchFilterHelper(LocalizeServicePT ls) {
        return new SearchFilterHelper<ProduitKey>(ls);
    }


    @Bean
    public HelpersUsecases helpersUsecases(SearchFilterHelper<ClientKey> clientSearchFilterHelper,
                                           SearchFilterHelper<ProduitKey> produitSearchFilterHelper
    ) {

        return HelpersUsecases.builder()
                .clientSearchFilterHelper(clientSearchFilterHelper)
                .produitSearchFilterHelper(produitSearchFilterHelper)
                .build();

    }


}
