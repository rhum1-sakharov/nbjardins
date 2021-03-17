package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.DemandeDeDevisUE;
import usecases.devis.SaveDevisUE;
import usecases.personnes.artisans.FindByApplicationTokenUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.clients.SaveClientUE;
import usecases.referentiel.conditions.reglements.FindConditionByEmailArtisanUE;
import usecases.referentiel.taxes.FindTauxByEmailArtisanUE;
import usecases.uniquecode.GetUniqueCodeUE;


@Configuration
public class DemandeDevisConfig {

    Environment env;

    public DemandeDevisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DemandeDeDevisUE realiserDevis(TransactionManagerPT transactionManager,
                                          LocalizeServicePT localizeService,
                                          MailDevisServicePT mailDevisService,
                                          SaveClientUE saveClientUE,
                                          GetUniqueCodeUE getUniqueCodeUE,
                                          FindByEmailUE artisanFindByEmailUE,
                                          FindByApplicationTokenUE artisanFindByApplicationTokenUE,
                                          FindTauxByEmailArtisanUE findTauxByEmailArtisanUE,
                                          FindConditionByEmailArtisanUE findConditionByEmailArtisanUE,
                                          FindByEmailAndPrefereUE findByEmailAndPrefereUE,
                                          SaveDevisUE enregistrerDevisUE


    ) {
        return new DemandeDeDevisUE(
                transactionManager,
                localizeService,
                mailDevisService,
                saveClientUE,
                getUniqueCodeUE,
                artisanFindByEmailUE,
                artisanFindByApplicationTokenUE,
                findTauxByEmailArtisanUE,
                findConditionByEmailArtisanUE,
                findByEmailAndPrefereUE,
                enregistrerDevisUE
        );
    }


}
