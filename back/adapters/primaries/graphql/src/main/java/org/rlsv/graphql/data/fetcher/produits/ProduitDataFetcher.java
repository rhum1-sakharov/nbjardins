package org.rlsv.graphql.data.fetcher.produits;

import graphql.schema.DataFetcher;
import models.search.Search;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.produits.SearchProduitUE;

import java.util.Map;

public class ProduitDataFetcher {


    SearchProduitUE searchProduitUE;

    public ProduitDataFetcher(SearchProduitUE searchProduitUE) {
        this.searchProduitUE = searchProduitUE;
    }


    public DataFetcher produitSearchDataFetcher() {

        return dataFetchingEnvironment -> {

            Map<String, Object> args = dataFetchingEnvironment.getArgument("search");

            Search search = MapperUtils.fromMap(args, Search.class);

            return searchProduitUE.execute(null, search);

        };
    }
}


