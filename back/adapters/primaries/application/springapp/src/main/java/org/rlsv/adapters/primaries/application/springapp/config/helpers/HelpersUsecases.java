package org.rlsv.adapters.primaries.application.springapp.config.helpers;

import helpers.search.filters.SearchFilterHelper;
import keys.client.ClientKey;
import keys.produit.ProduitKey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HelpersUsecases {


    private SearchFilterHelper<ClientKey> clientSearchFilterHelper;

    private SearchFilterHelper<ProduitKey> produitSearchFilterHelper;

}
