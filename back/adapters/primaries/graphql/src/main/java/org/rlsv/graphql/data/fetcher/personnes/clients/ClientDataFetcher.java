package org.rlsv.graphql.data.fetcher.personnes.clients;

import domains.devis.DevisDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.clients.FindClientsOfArtisanUE;
import usecases.personnes.clients.ShareInfosDevisUE;

import java.util.Map;

public class ClientDataFetcher {

    FindClientsOfArtisanUE findClientsOfArtisanUE;

    ShareInfosDevisUE shareInfosDevisUE;

    public ClientDataFetcher(FindClientsOfArtisanUE findClientsOfArtisanUE, ShareInfosDevisUE shareInfosDevisUE) {
        this.findClientsOfArtisanUE = findClientsOfArtisanUE;
        this.shareInfosDevisUE = shareInfosDevisUE;
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

            return shareInfosDevisUE.execute(null, devis);
        };
    }


}


