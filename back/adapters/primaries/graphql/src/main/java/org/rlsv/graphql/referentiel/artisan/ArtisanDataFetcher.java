package org.rlsv.graphql.referentiel.artisan;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import usecases.personnes.artisans.FindByEmailUE;

public class ArtisanDataFetcher {

    FindByEmailUE findByEmailUE;

    public ArtisanDataFetcher(FindByEmailUE findByEmailUE) {
        this.findByEmailUE = findByEmailUE;

    }

    public DataFetcher artisanByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findByEmailUE.execute(null, email);
        };
    }

}


