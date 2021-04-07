package org.rlsv.graphql.data.fetcher.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.models.NbResult;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.devis.*;

import java.util.List;
import java.util.Map;

public class DevisDataFetcher {

    FindByEmailArtisanUE findByEmailArtisanUE;
    DeleteDevisUE deleteDevisUE;
    ChangeStatusDevisUE changeStatusDevisUE;
    SaveDevisUE saveDevisUE;
    CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE;
    FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE;
    CreateDevisATraiterUE createDevisATraiterUE;

    public DevisDataFetcher(FindByEmailArtisanUE findByEmailArtisanUE, DeleteDevisUE deleteDevisUE, ChangeStatusDevisUE changeStatusDevisUE, SaveDevisUE saveDevisUE, CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE, FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE, CreateDevisATraiterUE createDevisATraiterUE) {
        this.findByEmailArtisanUE = findByEmailArtisanUE;
        this.deleteDevisUE = deleteDevisUE;
        this.changeStatusDevisUE = changeStatusDevisUE;
        this.saveDevisUE = saveDevisUE;
        this.countByEmailArtisanAndStatutUE = countByEmailArtisanAndStatutUE;
        this.findByEmailArtisanAndStatutUE = findByEmailArtisanAndStatutUE;
        this.createDevisATraiterUE = createDevisATraiterUE;
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

    public DataFetcher findByEmailArtisanAndStatutDataFetcher() {

        return dataFetchingEnvironment -> {

            String emailArtisan = dataFetchingEnvironment.getArgument("emailArtisan");
            String statutDevisStr = dataFetchingEnvironment.getArgument("statutDevis");

            List<DevisDN> devisList = findByEmailArtisanAndStatutUE.execute(null, emailArtisan, STATUT_DEVIS.valueOf(statutDevisStr));

            return devisList;
        };

    }

    public DataFetcher createDevisATraiterDataFetcher() {

        return dataFetchingEnvironment -> {

            Map<String, Object> resultArtisan = dataFetchingEnvironment.getArgument("emailArtisan");
            String emailArtisan = (String) resultArtisan.get("email");

            Map<String, Object> resultClient = dataFetchingEnvironment.getArgument("emailClient");
            String emailClient = (String) resultClient.get("email");


            return this.createDevisATraiterUE.execute(null, emailArtisan, emailClient);
        };
    }
}


