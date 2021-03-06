package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.artisans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.repositories.personnes.roles.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.SaveArtisanUE;

@Configuration
public class EnregistrerArtisanConfig {

    @Bean
    public SaveArtisanUE enregistrerArtisanUE(PersonneRepoPT personneRepo,
                                              PersonneRoleRepoPT personneRoleRepo,
                                              LocalizeServicePT localizeService,
                                              ArtisanRepoPT artisanRepo,
                                              TransactionManagerPT transactionManager) {
        return new SaveArtisanUE(personneRepo, personneRoleRepo, localizeService, artisanRepo, transactionManager);
    }

}
