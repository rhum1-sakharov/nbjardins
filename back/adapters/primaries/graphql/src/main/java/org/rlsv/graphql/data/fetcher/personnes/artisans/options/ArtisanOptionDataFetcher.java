package org.rlsv.graphql.data.fetcher.personnes.artisans.options;

import domains.personnes.artisans.options.ArtisanOptionDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.options.FindByEmailUE;
import usecases.personnes.artisans.options.SaveOptionUE;

import java.util.Map;

public class ArtisanOptionDataFetcher {

    FindByEmailUE findByEmailUE;
    SaveOptionUE saveOptionUE;

    public ArtisanOptionDataFetcher(FindByEmailUE findByEmailUE, SaveOptionUE saveOptionUE) {
        this.findByEmailUE = findByEmailUE;
        this.saveOptionUE = saveOptionUE;
    }

    public DataFetcher artisanOptionFindByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findByEmailUE.execute(null, email);
        };
    }

    public DataFetcher saveArtisanOptionDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("artisanOption");

            ArtisanOptionDN artisanOption= MapperUtils.fromMap(args, ArtisanOptionDN.class);

            return saveOptionUE.execute(null,artisanOption);
        };
    }
}


