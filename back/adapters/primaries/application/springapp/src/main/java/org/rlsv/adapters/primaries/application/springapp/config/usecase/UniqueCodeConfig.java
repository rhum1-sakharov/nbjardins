package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.uniquecode.UniqueCodeUE;

@Configuration
public class UniqueCodeConfig {

    @Bean
    public UniqueCodeUE uniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        return new UniqueCodeUE(localizeService, transactionManager, devisRepo);
    }


}
