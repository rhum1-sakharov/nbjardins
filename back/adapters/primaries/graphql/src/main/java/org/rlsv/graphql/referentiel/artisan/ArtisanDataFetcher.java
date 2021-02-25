package org.rlsv.graphql.referentiel.artisan;

import domains.ArtisanDN;
import domains.PersonneDN;
import exceptions.CleanException;
import graphql.schema.DataFetcher;
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
            Map<String,Object> args= dataFetchingEnvironment.getArgument("artisan");
            String id= (String) args.get("id");
            Map<String,Object> personneMap= (Map<String, Object>) args.get("personne");
            String idPersonne = (String) personneMap.get("id");
            String telephone = (String) personneMap.get("numeroTelephone");

            ArtisanDN artisan = new ArtisanDN();
            artisan.setId(id);
            PersonneDN personne = new PersonneDN();
            personne.setId(idPersonne);
            personne.setNumeroTelephone(telephone);
            artisan.setPersonne(personne);

            return enregistrerArtisanUE.execute(null, artisan);
        };
    }
}


