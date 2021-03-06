package org.rlsv.graphql.referentiel.artisan;

import domains.personnes.artisans.ArtisanDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.SaveArtisanUE;

import java.util.Map;

public class ArtisanDataFetcher {

    FindByEmailUE findByEmailUE;
    SaveArtisanUE saveArtisanUE;

    public ArtisanDataFetcher(FindByEmailUE findByEmailUE, SaveArtisanUE saveArtisanUE) {
        this.findByEmailUE = findByEmailUE;
        this.saveArtisanUE = saveArtisanUE;

    }

    public DataFetcher artisanByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return findByEmailUE.execute(null, email);
        };
    }

    public DataFetcher saveArtisanDataFetcher() {

        return dataFetchingEnvironment -> {
            Map<String, Object> args = dataFetchingEnvironment.getArgument("artisan");

            ArtisanDN artisan = MapperUtils.fromMap(args, ArtisanDN.class);

            artisan = saveArtisanUE.execute(null, artisan);

            return artisan;
        };
    }
}


