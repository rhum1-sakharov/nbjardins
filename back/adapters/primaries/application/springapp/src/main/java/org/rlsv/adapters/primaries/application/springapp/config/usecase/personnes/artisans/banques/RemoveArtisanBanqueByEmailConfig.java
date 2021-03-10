package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.artisans.banques;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.banques.RemoveArtisanBanqueByEmailUE;

@Configuration
public class RemoveArtisanBanqueByEmailConfig {

    @Bean
    public RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanBanqueRepoPT artisanBanqueRepo) {
        return new RemoveArtisanBanqueByEmailUE(ls, tm, artisanBanqueRepo);
    }

}
