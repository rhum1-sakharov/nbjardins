package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.personnes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.FindByEmailUE;


@Configuration
public class PersonneFindByEmailConfig {

    @Bean(name = "personneFindByEmailUE")
    public FindByEmailUE personneFindByEmailUE(
            LocalizeServicePT localizeService,
            TransactionManagerPT transactionManager,
            PersonneRepoPT personneRepo
    ) {
        return new FindByEmailUE(localizeService, transactionManager, personneRepo);
    }

}
