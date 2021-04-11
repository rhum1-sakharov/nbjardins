package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.CreateDevisATraiterUE;
import usecases.devis.SaveDevisUE;
import usecases.devis.options.SaveOptionUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.uniquecode.GetUniqueCodeUE;

@Configuration
public class CreateDevisATraiterConfig {

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


}
