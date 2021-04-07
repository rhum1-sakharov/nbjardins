package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.RemoveDevisUE;

@Configuration
public class DeleteDevisConfig {

    @Bean
    public RemoveDevisUE deleteDevisUE(LocalizeServicePT localizeService,
                                       TransactionManagerPT transactionManager,
                                       DevisRepoPT devisRepo
    ) {
        return new RemoveDevisUE(localizeService, transactionManager, devisRepo);
    }


}
