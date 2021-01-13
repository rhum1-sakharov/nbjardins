package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.ClientRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.EnregistrerClientUE;

@Configuration
public class EnregistrerClientConfig {

    @Bean
    public EnregistrerClientUE enregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService, ClientRepoPT clientRepo, TransactionManagerPT transactionManager) {
        return new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService, clientRepo, transactionManager);
    }

}
