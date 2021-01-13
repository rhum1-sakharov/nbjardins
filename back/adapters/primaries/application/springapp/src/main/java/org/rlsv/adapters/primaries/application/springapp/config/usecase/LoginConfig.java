package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.ConditionDeReglementRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.RoleRepoPT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.login.LoginUE;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.clients.EnregistrerClientUE;

@Configuration
public class LoginConfig {



    @Bean
    public LoginUE getLoginUE(LocalizeServicePT localizeService,
                              TransactionManagerPT transactionManager,
                              ILoginPT loginPT,
                              PersonneRepoPT personneRepo,
                              EnregistrerClientUE enregistrerClientUE,
                              EnregistrerArtisanUE enregistrerArtisanUE,
                              ConditionDeReglementRepoPT conditionDeReglementRepo,
                              TaxeRepoPT taxeRepo,
                              RoleRepoPT roleRepo
    ) {
        return new LoginUE(localizeService, transactionManager, loginPT, personneRepo, enregistrerClientUE, enregistrerArtisanUE,conditionDeReglementRepo,taxeRepo,roleRepo);
    }


}
