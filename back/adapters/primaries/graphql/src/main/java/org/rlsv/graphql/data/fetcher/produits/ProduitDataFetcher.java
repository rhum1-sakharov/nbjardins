package org.rlsv.graphql.data.fetcher.personnes.clients;

import domains.devis.DevisDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import models.search.Search;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.clients.FindClientsOfArtisanUE;
import usecases.personnes.clients.SearchClientUE;
import usecases.personnes.clients.ShareInfosDevisUE;

import java.util.Map;

public class ClientDataFetcher {

    FindClientsOfArtisanUE findClientsOfArtisanUE;

    ShareInfosDevisUE shareInfosDevisUE;

    SearchClientUE searchClientUE;


    public ClientDataFetcher(FindClientsOfArtisanUE findClientsOfArtisanUE, ShareInfosDevisUE shareInfosDevisUE, SearchClientUE searchClientUE) {
        this.findClientsOfArtisanUE = findClientsOfArtisanUE;
        this.shareInfosDevisUE = shareInfosDevisUE;
        this.searchClientUE = searchClientUE;
    }

    public DataFetcher clientFindByEmailArtisanDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String emailArtisan = dataFetchingEnvironment.getArgument("emailArtisan");
            return findClientsOfArtisanUE.execute(null, emailArtisan);
        };
    }

    public DataFetcher clientShareInfosDevisDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {

            Map<String, Object> args = dataFetchingEnvironment.getArgument("devis");

            DevisDN devis = MapperUtils.fromMap(args, DevisDN.class);

            Map<String, Object> map = shareInfosDevisUE.execute(null, devis);

            return map.get(ShareInfosDevisUE.CLIENT);
        };
    }


    public DataFetcher clientSearchDataFetcher() {

        return dataFetchingEnvironment -> {

            Map<String, Object> args = dataFetchingEnvironment.getArgument("search");

            Search search = MapperUtils.fromMap(args, Search.class);

            return searchClientUE.execute(null, search);

        };
    }
}


