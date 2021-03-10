package org.rlsv.graphql.data.fetcher.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.apache.commons.collections4.CollectionUtils;
import org.rlsv.graphql.models.Deleted;
import org.rlsv.graphql.utils.MapperUtils;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.artisans.banques.FindByEmailUE;
import usecases.personnes.artisans.banques.RemoveArtisanBanqueByEmailUE;
import usecases.personnes.artisans.banques.SaveArtisanBanqueUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArtisanBanqueDataFetcher {

    FindByEmailAndPrefereUE findByEmailAndPrefereUE;
    FindByEmailUE findByEmailUE;
    SaveArtisanBanqueUE saveArtisanBanqueUE;
    RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE;

    public ArtisanBanqueDataFetcher(FindByEmailAndPrefereUE findByEmailAndPrefereUE, FindByEmailUE findByEmailUE, SaveArtisanBanqueUE saveArtisanBanqueUE, RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE) {
        this.findByEmailAndPrefereUE = findByEmailAndPrefereUE;
        this.findByEmailUE = findByEmailUE;
        this.saveArtisanBanqueUE = saveArtisanBanqueUE;
        this.removeArtisanBanqueByEmailUE = removeArtisanBanqueByEmailUE;
    }

    public DataFetcher artisanBanqueFindByEmailAndPrefereDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            boolean prefere = dataFetchingEnvironment.getArgument("prefere");
            List<ArtisanBanqueDN> artisanBanqueDNList = findByEmailAndPrefereUE.execute(null, email, prefere);

            if (CollectionUtils.isEmpty(artisanBanqueDNList)) {
                return null;
            }

            return artisanBanqueDNList.get(0);
        };
    }

    public DataFetcher artisanBanqueFindByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");

            return findByEmailUE.execute(null, email);

        };
    }

    public DataFetcher removeArtisanBanqueByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {

            Map<String,Object> result = dataFetchingEnvironment.getArgument("email");

            Integer nbDeleted= removeArtisanBanqueByEmailUE.execute(null, (String) result.get("email"));

            return new Deleted(nbDeleted);

        };
    }


    public DataFetcher saveArtisanBanqueListDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {

            List<Map<String,Object>> result = dataFetchingEnvironment.getArgument("artisanBanqueList");


            List<ArtisanBanqueDN> savedArtisanBanqueDNList = new ArrayList<>();

            for (Map<String,Object> item : result) {
                ArtisanBanqueDN artisanBanque = MapperUtils.fromMap(item,ArtisanBanqueDN.class);
                savedArtisanBanqueDNList.add(saveArtisanBanqueUE.execute(null,artisanBanque));
            }

            return savedArtisanBanqueDNList;

        };
    }

}