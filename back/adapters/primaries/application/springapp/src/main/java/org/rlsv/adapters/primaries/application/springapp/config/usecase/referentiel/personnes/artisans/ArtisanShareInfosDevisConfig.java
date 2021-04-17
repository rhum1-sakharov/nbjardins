package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.artisans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.SaveArtisanUE;
import usecases.personnes.artisans.ShareInfosDevisUE;


@Configuration
public class ArtisanShareInfosDevisConfig {

    @Bean
    public ShareInfosDevisUE artisanShareInfosDevisUE(LocalizeServicePT localizeService,
                                                      TransactionManagerPT transactionManager,
                                                      SaveArtisanUE saveArtisanUE,
                                                      FindByEmailUE findByEmailUE

    ) {
        return new ShareInfosDevisUE(localizeService, transactionManager, saveArtisanUE, findByEmailUE);
    }

}
