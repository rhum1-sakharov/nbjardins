package org.rlsv.graphql.referentiel.artisan.banque;

import domains.ArtisanBanqueDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.apache.commons.collections4.CollectionUtils;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;

import java.util.List;

public class ArtisanBanqueDataFetcher {

    FindByEmailAndPrefereUE findByEmailAndPrefereUE;

    public ArtisanBanqueDataFetcher(FindByEmailAndPrefereUE findByEmailAndPrefereUE) {
        this.findByEmailAndPrefereUE = findByEmailAndPrefereUE;
    }

    public DataFetcher artisanBanqueByEmailAndPrefereDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            boolean prefere = dataFetchingEnvironment.getArgument("prefere");
           List<ArtisanBanqueDN> artisanBanqueDNList= findByEmailAndPrefereUE.execute(null, email, prefere);

           if(CollectionUtils.isEmpty(artisanBanqueDNList)){
               return null;
           }

           return artisanBanqueDNList.get(0);
        };
    }

}