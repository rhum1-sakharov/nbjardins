package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes.artisans.banques;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.banques.SaveArtisanBanqueUE;

@Configuration
public class SaveArtisanBanqueConfig {

    @Bean
    public SaveArtisanBanqueUE saveArtisanBanqueUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanBanqueRepoPT artisanBanqueRepo) {
        return new SaveArtisanBanqueUE(ls, tm, artisanBanqueRepo);
    }

}
