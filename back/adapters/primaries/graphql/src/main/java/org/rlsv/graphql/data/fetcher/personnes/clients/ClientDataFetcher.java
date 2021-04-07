package org.rlsv.graphql.data.fetcher.personnes.clients;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import usecases.personnes.clients.FindClientsOfArtisanUE;

public class ClientDataFetcher {

    FindClientsOfArtisanUE findClientsOfArtisanUE;

    public ClientDataFetcher(FindClientsOfArtisanUE findClientsOfArtisanUE) {
        this.findClientsOfArtisanUE = findClientsOfArtisanUE;
    }

    public DataFetcher clientFindByEmailArtisanDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String emailArtisan = dataFetchingEnvironment.getArgument("emailArtisan");
            return findClientsOfArtisanUE.execute(null, emailArtisan);
        };
    }


}


