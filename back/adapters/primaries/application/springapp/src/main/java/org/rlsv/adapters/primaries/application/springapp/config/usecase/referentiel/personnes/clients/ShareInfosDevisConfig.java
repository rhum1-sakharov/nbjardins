package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.FindByEmailUE;
import usecases.personnes.clients.SaveClientUE;
import usecases.personnes.clients.ShareInfosDevisUE;

@Configuration
public class ShareInfosDevisConfig {

    @Bean
    public ShareInfosDevisUE shareInfosDevisUE(LocalizeServicePT localizeService,
                                               TransactionManagerPT transactionManager,
                                               SaveClientUE saveClientUE,
                                               FindByEmailUE clientFindByEmail
    ) {
        return new ShareInfosDevisUE(localizeService, transactionManager, saveClientUE, clientFindByEmail);
    }

}
