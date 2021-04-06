package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.FindByEmailArtisanAndStatutUE;

@Configuration
public class FindByEmailArtisanAndStatutConfig {

    @Bean
    public FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE(LocalizeServicePT localizeService,
                                                                 TransactionManagerPT transactionManager,
                                                                 DevisRepoPT devisRepo
    ) {
        return new FindByEmailArtisanAndStatutUE(localizeService, transactionManager, devisRepo);
    }


}
