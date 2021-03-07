package org.rlsv.graphql.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
import org.apache.commons.collections4.CollectionUtils;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.artisans.banques.FindByEmailUE;

import java.util.List;

public class ArtisanBanqueDataFetcher {

    FindByEmailAndPrefereUE findByEmailAndPrefereUE;
    FindByEmailUE findByEmailUE;

    public ArtisanBanqueDataFetcher(FindByEmailAndPrefereUE findByEmailAndPrefereUE, FindByEmailUE findByEmailUE) {
        this.findByEmailAndPrefereUE = findByEmailAndPrefereUE;
        this.findByEmailUE = findByEmailUE;
    }

    public DataFetcher artisanBanqueFindByEmailAndPrefereDataFetcher() throws CleanException {
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

    public DataFetcher artisanBanqueFindByEmailDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");

            return findByEmailUE.execute(null, email);

        };
    }

}