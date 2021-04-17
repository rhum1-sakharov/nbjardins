package org.rlsv.graphql.data.fetcher.personnes.artisans;

import domains.devis.DevisDN;
import domains.personnes.artisans.ArtisanDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.SaveArtisanUE;
import usecases.personnes.artisans.ShareInfosDevisUE;

import java.util.Map;

public class ArtisanDataFetcher {

    FindByEmailUE findByEmailUE;
    SaveArtisanUE saveArtisanUE;
    ShareInfosDevisUE artisanShareInfosDevisUE;

    public ArtisanDataFetcher(FindByEmailUE findByEmailUE, SaveArtisanUE saveArtisanUE, ShareInfosDevisUE artisanShareInfosDevisUE) {
        this.findByEmailUE = findByEmailUE;
        this.saveArtisanUE = saveArtisanUE;
        this.artisanShareInfosDevisUE = artisanShareInfosDevisUE;
    }

    public DataFetcher artisanFindByEmailDataFetcher() throws CleanException {
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

    public DataFetcher artisanShareInfosDevisDataFetcher() {

        return dataFetchingEnvironment -> {

            Map<String, Object> args = dataFetchingEnvironment.getArgument("devis");

            DevisDN devis = MapperUtils.fromMap(args, DevisDN.class);

            return artisanShareInfosDevisUE.execute(null, devis);

        };
    }
}


