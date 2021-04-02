package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis.options;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.options.SaveOptionUE;

@Configuration
public class DevisSaveOptionConfig {

    @Bean
    public SaveOptionUE devisSaveOptionUE(LocalizeServicePT localizeService,
                                    TransactionManagerPT transactionManager,
                                    DevisOptionRepoPT devisOptionRepo
    ) {
        return new SaveOptionUE(localizeService, transactionManager, devisOptionRepo);
    }

}
