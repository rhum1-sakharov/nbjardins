package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.transactions.TransactionManagerPT;
import usecases.authorization.GetAuthorizationUE;
import usecases.personnes.FindByEmailUE;
import usecases.personnes.artisans.SaveArtisanUE;
import usecases.personnes.clients.SaveClientUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.roles.FindByPersonneUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

@Configuration
public class LoginConfig {


    @Bean
    public GetAuthorizationUE getLoginUE(LocalizeServicePT localizeService,
                                         TransactionManagerPT transactionManager,
                                         ILoginPT loginPT,
                                         FindByEmailUE personneFindByEmailUE,
                                         SaveClientUE saveClientUE,
                                         SaveArtisanUE saveArtisanUE,
                                         FindAllConditionReglementUE findAllConditionReglementUE,
                                         usecases.personnes.artisans.FindByEmailUE artisanFindByEmailUE,
                                         FindAllTaxeUE findAllTaxeUE,
                                         FindByPersonneUE rolesFindByPersonneUE
    ) {
        return new GetAuthorizationUE(localizeService, transactionManager, loginPT, personneFindByEmailUE, saveClientUE, saveArtisanUE, findAllConditionReglementUE, findAllTaxeUE, artisanFindByEmailUE, rolesFindByPersonneUE);
    }


}
