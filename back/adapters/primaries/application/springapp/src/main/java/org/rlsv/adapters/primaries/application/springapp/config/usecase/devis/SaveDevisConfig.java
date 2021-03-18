package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.SaveDevisUE;

@Configuration
public class SaveDevisConfig {

    @Bean
    public SaveDevisUE saveDevisUE(LocalizeServicePT localizeService,
                                     TransactionManagerPT transactionManager,
                                     DevisRepoPT devisRepo
    ) {
        return new SaveDevisUE(localizeService, transactionManager, devisRepo);
    }


}
