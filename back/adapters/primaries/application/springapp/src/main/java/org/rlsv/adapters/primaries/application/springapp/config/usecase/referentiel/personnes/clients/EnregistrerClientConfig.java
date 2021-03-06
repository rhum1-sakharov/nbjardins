package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.repositories.personnes.roles.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.SaveClientUE;

@Configuration
public class EnregistrerClientConfig {

    @Bean
    public SaveClientUE enregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService, ClientRepoPT clientRepo, TransactionManagerPT transactionManager) {
        return new SaveClientUE(personneRepo, personneRoleRepo, localizeService, clientRepo, transactionManager);
    }

}
