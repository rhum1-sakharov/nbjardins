package org.rlsv.graphql.data.fetcher.devis;

import domains.devis.DevisDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.devis.ChangeStatusDevisUE;
import usecases.devis.DeleteDevisUE;
import usecases.devis.FindByEmailArtisanUE;
import usecases.devis.SaveDevisUE;

import java.util.Map;

public class DevisDataFetcher {

    FindByEmailArtisanUE findByEmailArtisanUE;
    DeleteDevisUE deleteDevisUE;
    ChangeStatusDevisUE changeStatusDevisUE;
    SaveDevisUE saveDevisUE;

    public DevisDataFetcher(FindByEmailArtisanUE findByEmailArtisanUE, DeleteDevisUE deleteDevisUE, ChangeStatusDevisUE changeStatusDevisUE, SaveDevisUE saveDevisUE) {
        this.findByEmailArtisanUE = findByEmailArtisanUE;
        this.deleteDevisUE = deleteDevisUE;
        this.changeStatusDevisUE = changeStatusDevisUE;
        this.saveDevisUE = saveDevisUE;
    }

    public DataFetcher findByEmailArtisanDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findByEmailArtisanUE.execute(null, email);
        };
    }

    public DataFetcher saveDevisDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("devis");

            DevisDN devis= MapperUtils.fromMap(args, DevisDN.class);

            devis = saveDevisUE.execute(null, devis);

            return devis;
        };
    }
}


