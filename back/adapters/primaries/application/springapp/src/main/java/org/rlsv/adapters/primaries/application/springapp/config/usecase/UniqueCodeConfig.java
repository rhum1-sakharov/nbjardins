package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.uniquecode.GetUniqueCodeUE;

@Configuration
public class UniqueCodeConfig {

    @Bean
    public GetUniqueCodeUE uniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        return new GetUniqueCodeUE(localizeService, transactionManager, devisRepo);
    }


}
