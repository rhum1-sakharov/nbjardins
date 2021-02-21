package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.EnregistrerUE;

@Configuration
public class EnregistrerDevisConfig {

    @Bean
    public EnregistrerUE enregistrerUE(LocalizeServicePT localizeService,
                                    TransactionManagerPT transactionManager,
                                    DevisRepoPT devisRepo
    ) {
        return new EnregistrerUE(localizeService, transactionManager, devisRepo);
    }


}
