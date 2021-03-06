package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.artisans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.FindByEmailUE;

@Configuration
public class ArtisanFindByEmailConfig {

    @Bean(name="artisanFindByEmailUE")
    public FindByEmailUE artisanFindByEmailUE(
            LocalizeServicePT localizeService,
            TransactionManagerPT transactionManager,
            ArtisanRepoPT artisanRepo
    ) {
        return new FindByEmailUE(localizeService, transactionManager, artisanRepo);
    }

}
