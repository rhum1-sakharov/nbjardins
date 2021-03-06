package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.artisans.banques;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;

@Configuration
public class FindByEmailAndPrefereConfig {

    @Bean
    public FindByEmailAndPrefereUE banqueFindByEmailAndPrefereUE(
            LocalizeServicePT localizeService,
            TransactionManagerPT transactionManager,
            ArtisanBanqueRepoPT artisanBanqueRepo
            ) {
        return new FindByEmailAndPrefereUE(localizeService, transactionManager, artisanBanqueRepo);
    }

}
