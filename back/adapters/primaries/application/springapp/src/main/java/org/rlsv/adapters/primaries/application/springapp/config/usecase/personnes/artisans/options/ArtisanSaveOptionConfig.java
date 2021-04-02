package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.artisans.options;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.options.SaveOptionUE;

@Configuration
public class ArtisanSaveOptionConfig {

    @Bean
    public SaveOptionUE artisanSaveOptionUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanOptionRepoPT artisanOptionRepo) {
        return new SaveOptionUE(ls, tm, artisanOptionRepo);
    }

}
