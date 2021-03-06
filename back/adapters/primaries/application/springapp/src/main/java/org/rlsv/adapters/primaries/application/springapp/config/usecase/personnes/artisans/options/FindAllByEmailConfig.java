package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.artisans.options;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.options.FindAllByEmailUE;

@Configuration
public class FindAllByEmailConfig {

    @Bean
    public FindAllByEmailUE findAllByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanOptionRepoPT artisanOptionRepo) {
        return new FindAllByEmailUE(ls, tm, artisanOptionRepo);
    }

}
