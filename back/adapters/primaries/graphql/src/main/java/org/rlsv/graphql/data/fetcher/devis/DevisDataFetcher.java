package org.rlsv.graphql.data.fetcher.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.models.NbResult;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.devis.*;

import java.util.Map;

public class DevisDataFetcher {

    FindByEmailArtisanUE findByEmailArtisanUE;
    DeleteDevisUE deleteDevisUE;
    ChangeStatusDevisUE changeStatusDevisUE;
    SaveDevisUE saveDevisUE;
    CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE;

    public DevisDataFetcher(FindByEmailArtisanUE findByEmailArtisanUE, DeleteDevisUE deleteDevisUE, ChangeStatusDevisUE changeStatusDevisUE, SaveDevisUE saveDevisUE, CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE) {
        this.findByEmailArtisanUE = findByEmailArtisanUE;
        this.deleteDevisUE = deleteDevisUE;
        this.changeStatusDevisUE = changeStatusDevisUE;
        this.saveDevisUE = saveDevisUE;
        this.countByEmailArtisanAndStatutUE = countByEmailArtisanAndStatutUE;
    }

    public DataFetcher findByEmailArtisanDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findByEmailArtisanUE.execute(null, email);
        };
    }

    public DataFetcher countByEmailArtisanAndStatutDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String emailArtisan = dataFetchingEnvironment.getArgument("emailArtisan");
            String statutDevisStr = dataFetchingEnvironment.getArgument("statutDevis");
            Long nbResult = countByEmailArtisanAndStatutUE.execute(null, emailArtisan, STATUT_DEVIS.valueOf(statutDevisStr));

            return new NbResult(nbResult);
        };
    }

    public DataFetcher saveDevisDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("devis");

            DevisDN devis = MapperUtils.fromMap(args, DevisDN.class);

            devis = saveDevisUE.execute(null, devis);

            return devis;
        };
    }
}


