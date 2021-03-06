package org.rlsv.graphql.referentiel.artisan;

import domains.personnes.artisans.ArtisanDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.artisans.FindByEmailUE;

import java.util.Map;

public class ArtisanDataFetcher {

    FindByEmailUE findByEmailUE;
    EnregistrerArtisanUE enregistrerArtisanUE;

    public ArtisanDataFetcher(FindByEmailUE findByEmailUE, EnregistrerArtisanUE enregistrerArtisanUE) {
        this.findByEmailUE = findByEmailUE;
        this.enregistrerArtisanUE = enregistrerArtisanUE;

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

            artisan = enregistrerArtisanUE.execute(null, artisan);

            return artisan;
        };
    }
}


