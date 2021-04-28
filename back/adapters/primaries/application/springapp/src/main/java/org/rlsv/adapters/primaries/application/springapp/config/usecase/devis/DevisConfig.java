package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.repositories.devis.DevisRepoPT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.*;
import usecases.devis.options.FindAllByDevisUE;
import usecases.devis.options.SaveOptionUE;
import usecases.personnes.artisans.FindByApplicationTokenUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.clients.SaveClientUE;
import usecases.referentiel.conditions.reglements.FindConditionByEmailArtisanUE;
import usecases.referentiel.taxes.FindTauxByEmailArtisanUE;
import usecases.uniquecode.GetUniqueCodeUE;

@Configuration
public class DevisConfig {

    Environment env;


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

    @Bean
    public ChangeStatusDevisUE changeStatusDevisUE(LocalizeServicePT localizeService,
                                                   TransactionManagerPT transactionManager,
                                                   DevisRepoPT devisRepo
    ) {
        return new ChangeStatusDevisUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE(LocalizeServicePT localizeService,
                                                                         TransactionManagerPT transactionManager,
                                                                         DevisRepoPT devisRepo
    ) {
        return new CountByEmailArtisanAndStatutUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public CreateDevisATraiterUE createDevisATraiterUE(LocalizeServicePT ls,
                                                       TransactionManagerPT transactionManager,
                                                       SaveDevisUE saveDevisUE,
                                                       SaveOptionUE saveOptionUE,
                                                       FindByEmailUE artisanfindByEmailUE,
                                                       FindByEmailAndPrefereUE artisanBanqueFindByEmailAndPrefereUE,
                                                       usecases.personnes.clients.FindByEmailUE clientFindByEmailUE,
                                                       GetUniqueCodeUE getUniqueCodeUE,
                                                       usecases.personnes.artisans.options.FindByEmailUE aoFindByEmailUE
    ) {
        return new CreateDevisATraiterUE(ls,
                transactionManager,
                saveDevisUE,
                saveOptionUE,
                artisanfindByEmailUE,
                artisanBanqueFindByEmailAndPrefereUE,
                clientFindByEmailUE,
                getUniqueCodeUE,
                aoFindByEmailUE
        );
    }

    @Bean
    public RemoveDevisUE deleteDevisUE(LocalizeServicePT localizeService,
                                       TransactionManagerPT transactionManager,
                                       DevisRepoPT devisRepo
    ) {
        return new RemoveDevisUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE(LocalizeServicePT localizeService,
                                                                       TransactionManagerPT transactionManager,
                                                                       DevisRepoPT devisRepo
    ) {
        return new FindByEmailArtisanAndStatutUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public FindByEmailArtisanUE findByEmailArtisanUE(LocalizeServicePT localizeService,
                                                     TransactionManagerPT transactionManager,
                                                     DevisRepoPT devisRepo
    ) {
        return new FindByEmailArtisanUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public FindByIdDevisUE findByIdDevisUE(LocalizeServicePT localizeService,
                                           TransactionManagerPT transactionManager,
                                           DevisRepoPT devisRepo
    ) {
        return new FindByIdDevisUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public GenerateDevisPdfUE generateDevisPdfUE(LocalizeServicePT localizeService,
                                                 TransactionManagerPT transactionManager,
                                                 ProviderPdfPT providerPdfPT
    ) {
        return new GenerateDevisPdfUE(localizeService, transactionManager, providerPdfPT);
    }

    @Bean
    public SaveDevisUE saveDevisUE(LocalizeServicePT localizeService,
                                   TransactionManagerPT transactionManager,
                                   DevisRepoPT devisRepo
    ) {
        return new SaveDevisUE(localizeService, transactionManager, devisRepo);
    }

    @Bean
    public SaveOptionUE devisSaveOptionUE(LocalizeServicePT localizeService,
                                          TransactionManagerPT transactionManager,
                                          DevisOptionRepoPT devisOptionRepo
    ) {
        return new SaveOptionUE(localizeService, transactionManager, devisOptionRepo);
    }

    @Bean
    public FindAllByDevisUE devisOptionFindAllByDevisUE(LocalizeServicePT localizeService,
                                                        TransactionManagerPT transactionManager,
                                                        DevisOptionRepoPT devisOptionRepo
    ) {
        return new FindAllByDevisUE(localizeService, transactionManager, devisOptionRepo);
    }

    @Bean
    public DevisUsecases devisUsecases(ChangeStatusDevisUE changeStatusDevisUE,
                                       CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE,
                                       CreateDevisATraiterUE createDevisATraiterUE,
                                       RemoveDevisUE deleteDevisUE,
                                       FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE,
                                       FindByEmailArtisanUE findByEmailArtisanUE,
                                       FindByIdDevisUE findByIdDevisUE,
                                       GenerateDevisPdfUE generateDevisPdfUE,
                                       SaveDevisUE saveDevisUE,
                                       SaveOptionUE devisSaveOptionUE,
                                       FindAllByDevisUE devisOptionFindAllByDevisUE,
                                       DemandeDeDevisUE realiserDevis
    ) {

        return DevisUsecases.builder()
                .changeStatusDevisUE(changeStatusDevisUE)
                .countByEmailArtisanAndStatutUE(countByEmailArtisanAndStatutUE)
                .createDevisATraiterUE(createDevisATraiterUE)
                .deleteDevisUE(deleteDevisUE)
                .findByEmailArtisanAndStatutUE(findByEmailArtisanAndStatutUE)
                .findByEmailArtisanUE(findByEmailArtisanUE)
                .findByIdDevisUE(findByIdDevisUE)
                .generateDevisPdfUE(generateDevisPdfUE)
                .saveDevisUE(saveDevisUE)
                .devisSaveOptionUE(devisSaveOptionUE)
                .devisOptionFindAllByDevisUE(devisOptionFindAllByDevisUE)
                .demandeDeDevisUE(realiserDevis)
                .build();
    }

}
