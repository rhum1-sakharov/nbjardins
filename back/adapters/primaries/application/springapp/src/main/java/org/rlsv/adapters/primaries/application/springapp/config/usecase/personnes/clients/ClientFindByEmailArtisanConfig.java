package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.clients.FindClientsOfArtisanUE;


@Configuration
public class ClientFindByEmailArtisanConfig {

    @Bean
    public FindClientsOfArtisanUE findClientsOfArtisanUE(LocalizeServicePT ls, TransactionManagerPT tm, ClientRepoPT clientRepo) {
        return new FindClientsOfArtisanUE(ls, tm, clientRepo);
    }

}
