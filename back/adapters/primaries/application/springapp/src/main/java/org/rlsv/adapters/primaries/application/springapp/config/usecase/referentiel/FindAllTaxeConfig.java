package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.referentiel.taxes.FindAllTaxeUE;

@Configuration
public class FindAllTaxeConfig {

    @Bean
    public FindAllTaxeUE findAllTaxeUE(LocalizeServicePT localizeService,
                                       TransactionManagerPT transactionManager,
                                       TaxeRepoPT taxeRepo
    ) {
        return new FindAllTaxeUE(localizeService, transactionManager, taxeRepo);
    }


}
