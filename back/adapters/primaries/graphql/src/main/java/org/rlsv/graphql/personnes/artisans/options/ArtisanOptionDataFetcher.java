package org.rlsv.graphql.personnes.artisans.options;

import domains.personnes.artisans.options.ArtisanOptionDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.options.FindAllByEmailUE;
import usecases.personnes.artisans.options.SaveOptionUE;

import java.util.Map;

public class ArtisanOptionDataFetcher {

    FindAllByEmailUE findAllByEmailUE;
    SaveOptionUE saveOptionUE;

    public ArtisanOptionDataFetcher(FindAllByEmailUE findAllByEmailUE, SaveOptionUE saveOptionUE) {
        this.findAllByEmailUE = findAllByEmailUE;
        this.saveOptionUE = saveOptionUE;
    }

    public DataFetcher artisanOptionFindAllByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findAllByEmailUE.execute(null, email);
        };
    }

    public DataFetcher saveArtisanOptionDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("artisanOption");

            ArtisanOptionDN artisanOption= MapperUtils.fromMap(args, ArtisanOptionDN.class);

            return saveOptionUE.execute(null, artisanOption.getId(),artisanOption.getModeleOption(),artisanOption.isActif());
        };
    }
}


