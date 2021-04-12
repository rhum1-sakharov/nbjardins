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

import static usecases.devis.CreateDevisATraiterUE.DEVIS;

public class DevisDataFetcher {

    FindByEmailArtisanUE findByEmailArtisanUE;
    RemoveDevisUE removeDevisUE;
    ChangeStatusDevisUE changeStatusDevisUE;
    SaveDevisUE saveDevisUE;
    CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE;
    FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE;
    CreateDevisATraiterUE createDevisATraiterUE;
    FindByIdDevisUE findByIdDevisUE;


    public DevisDataFetcher(FindByEmailArtisanUE findByEmailArtisanUE, RemoveDevisUE removeDevisUE, ChangeStatusDevisUE changeStatusDevisUE, SaveDevisUE saveDevisUE, CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE, FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE, CreateDevisATraiterUE createDevisATraiterUE, FindByIdDevisUE findByIdDevisUE) {
        this.findByEmailArtisanUE = findByEmailArtisanUE;
        this.removeDevisUE = removeDevisUE;
        this.changeStatusDevisUE = changeStatusDevisUE;
        this.saveDevisUE = saveDevisUE;
        this.countByEmailArtisanAndStatutUE = countByEmailArtisanAndStatutUE;
        this.findByEmailArtisanAndStatutUE = findByEmailArtisanAndStatutUE;
        this.createDevisATraiterUE = createDevisATraiterUE;
        this.findByIdDevisUE = findByIdDevisUE;
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


            Map<String, Object> results = this.createDevisATraiterUE.execute(null, emailArtisan, emailClient);

            DevisDN devis = (DevisDN) results.get(DEVIS);

            return devis;
        };
    }

    public DataFetcher removeDevisDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> result = dataFetchingEnvironment.getArgument("idDevis");
            String idDevis = (String) result.get("id");

            return removeDevisUE.execute(null, idDevis);

        };

    }

    public DataFetcher findByIdDataFetcher() {

        return dataFetchingEnvironment -> {

            String idArtisan= dataFetchingEnvironment.getArgument("idDevis");

            DevisDN devis= findByIdDevisUE.execute(null, idArtisan);

            return devis;
        };

    }


}


