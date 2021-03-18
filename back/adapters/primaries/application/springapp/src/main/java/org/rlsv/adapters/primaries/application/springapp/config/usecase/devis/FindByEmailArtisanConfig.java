package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.FindByEmailArtisanUE;

@Configuration
public class FindByEmailArtisanConfig {

    @Bean
    public FindByEmailArtisanUE findByEmailArtisanUE(LocalizeServicePT localizeService,
                                                    TransactionManagerPT transactionManager,
                                                    DevisRepoPT devisRepo
    ) {
        return new FindByEmailArtisanUE(localizeService, transactionManager, devisRepo);
    }


}
