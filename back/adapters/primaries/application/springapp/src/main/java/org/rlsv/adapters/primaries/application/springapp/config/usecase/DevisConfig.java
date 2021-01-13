package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.*;
import ports.transactions.TransactionManagerPT;
import usecases.devis.DemandeDeDevisUE;
import usecases.personnes.clients.EnregistrerClientUE;
import usecases.uniquecode.UniqueCodeUE;


@Configuration
public class DevisConfig {

    Environment env;

    public DevisConfig(Environment env) {
        this.env = env;
    }






    @Bean
    public DemandeDeDevisUE realiserDevis(MailDevisServicePT mailDevisService,
                                          LocalizeServicePT localizeService,
                                          DevisRepoPT demandeDeDevisRepo,
                                          EnregistrerClientUE enregistrerClientUE,
                                          TransactionManagerPT transactionManager,
                                          TaxeRepoPT taxeRepo,
                                          UniqueCodeUE uniqueCodeUE,
                                          ArtisanBanqueRepoPT artisanBanqueRepo,
                                          ConditionDeReglementRepoPT conditionDeReglementRepo,
                                          ArtisanRepoPT artisanRepo
    ) {
        return new DemandeDeDevisUE(mailDevisService,
                localizeService,
                demandeDeDevisRepo,
                enregistrerClientUE,
                transactionManager,
                taxeRepo,
                uniqueCodeUE,
                artisanBanqueRepo,
                conditionDeReglementRepo,
                artisanRepo
        );
    }


}
