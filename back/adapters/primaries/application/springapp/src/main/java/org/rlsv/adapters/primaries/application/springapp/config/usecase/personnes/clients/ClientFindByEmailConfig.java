package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.FindByEmailUE;


@Configuration
public class ClientFindByEmailConfig {

    @Bean
    public FindByEmailUE clientFindByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ClientRepoPT clientRepo) {
        return new FindByEmailUE(ls, tm, clientRepo);
    }

}
