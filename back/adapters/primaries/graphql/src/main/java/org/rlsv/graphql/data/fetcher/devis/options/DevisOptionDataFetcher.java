package org.rlsv.graphql.data.fetcher.devis.options;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import usecases.devis.options.FindAllByDevisUE;

public class DevisOptionDataFetcher {

    FindAllByDevisUE doFindAllByDevisUE;

    public DevisOptionDataFetcher(FindAllByDevisUE doFindAllByDevisUE) {
        this.doFindAllByDevisUE = doFindAllByDevisUE;
    }

    public DataFetcher findByIdDevisDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String idDevis = dataFetchingEnvironment.getArgument("idDevis");
            return doFindAllByDevisUE.execute(null, idDevis);
        };
    }


}


