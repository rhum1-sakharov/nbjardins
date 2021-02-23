package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.RoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.referentiel.roles.FindByPersonneUE;


@Configuration
public class FindByPersonneConfig {

    @Bean
    public FindByPersonneUE roleFindByPersonneUE(
            LocalizeServicePT localizeService,
            TransactionManagerPT transactionManager,
            RoleRepoPT roleRepo
    ) {
        return new FindByPersonneUE(localizeService, transactionManager, roleRepo);
    }

}
