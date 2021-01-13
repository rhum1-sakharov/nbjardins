package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.ArtisanRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.EnregistrerArtisanUE;

@Configuration
public class EnregistrerArtisanConfig {

    @Bean
    public EnregistrerArtisanUE enregistrerArtisanUE(PersonneRepoPT personneRepo,
                                                     PersonneRoleRepoPT personneRoleRepo,
                                                     LocalizeServicePT localizeService,
                                                     ArtisanRepoPT artisanRepo,
                                                     TransactionManagerPT transactionManager) {
        return new EnregistrerArtisanUE(personneRepo, personneRoleRepo, localizeService, artisanRepo, transactionManager);
    }

}
