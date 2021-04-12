package org.rlsv.graphql.data.fetcher.devis.options;

import domains.devis.options.DevisOptionDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.devis.options.FindAllByDevisUE;
import usecases.devis.options.SaveOptionUE;

import java.util.Map;

public class DevisOptionDataFetcher {

    FindAllByDevisUE doFindAllByDevisUE;
    SaveOptionUE doSaveOptionUE;

    public DevisOptionDataFetcher(FindAllByDevisUE doFindAllByDevisUE, SaveOptionUE doSaveOptionUE) {
        this.doFindAllByDevisUE = doFindAllByDevisUE;
        this.doSaveOptionUE = doSaveOptionUE;
    }

    public DataFetcher findByIdDevisDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String idDevis = dataFetchingEnvironment.getArgument("idDevis");
            return doFindAllByDevisUE.execute(null, idDevis);
        };
    }

    public DataFetcher saveDevisOptionDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("devisOption");

            DevisOptionDN devisOption = MapperUtils.fromMap(args, DevisOptionDN.class);

            return doSaveOptionUE.execute(null, devisOption);
        };

    }

}


