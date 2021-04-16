package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.SaveClientUE;

@Configuration
public class SaveClientConfig {

    @Bean
    public SaveClientUE enregistrerClientUE(LocalizeServicePT localizeService, ClientRepoPT clientRepo, TransactionManagerPT transactionManager) {
        return new SaveClientUE(localizeService,transactionManager,clientRepo);
    }

}
