package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.artisans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.ArtisanRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.FindByApplicationTokenUE;

@Configuration
public class FindByApplicationTokenConfig {

    @Bean
    public FindByApplicationTokenUE artisanFindByApplicationTokenUE(
            LocalizeServicePT localizeService,
            TransactionManagerPT transactionManager,
            ArtisanRepoPT artisanRepo
    ) {
        return new FindByApplicationTokenUE(localizeService, transactionManager, artisanRepo);
    }

}
