package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.conditions.reglements;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;

@Configuration
public class FindAllConditionReglementConfig {

    @Bean
    public FindAllConditionReglementUE findAllConditionReglementUE(LocalizeServicePT localizeService,
                                                     TransactionManagerPT transactionManager,
                                                     ConditionDeReglementRepoPT conditionDeReglementRepo
    ) {
        return new FindAllConditionReglementUE(localizeService, transactionManager, conditionDeReglementRepo);
    }


}
