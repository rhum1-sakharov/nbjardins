package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.transactions.TransactionManagerPT;
import usecases.login.LoginUE;
import usecases.personnes.FindByEmailUE;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.clients.EnregistrerClientUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.roles.FindByPersonneUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

@Configuration
public class LoginConfig {


    @Bean
    public LoginUE getLoginUE(LocalizeServicePT localizeService,
                              TransactionManagerPT transactionManager,
                              ILoginPT loginPT,
                              FindByEmailUE personneFindByEmailUE,
                              EnregistrerClientUE enregistrerClientUE,
                              EnregistrerArtisanUE enregistrerArtisanUE,
                              FindAllConditionReglementUE findAllConditionReglementUE,
                              usecases.personnes.artisans.FindByEmailUE artisanFindByEmailUE,
                              FindAllTaxeUE findAllTaxeUE,
                              FindByPersonneUE rolesFindByPersonneUE
    ) {
        return new LoginUE(localizeService, transactionManager, loginPT, personneFindByEmailUE, enregistrerClientUE, enregistrerArtisanUE, findAllConditionReglementUE, findAllTaxeUE, artisanFindByEmailUE, rolesFindByPersonneUE);
    }


}
