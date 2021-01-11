package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.clients.EnregistrerClientUE;
import usecases.login.LoginUE;

@Configuration
public class LoginConfig {



    @Bean
    public LoginUE getLoginUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ILoginPT loginPT, PersonneRepoPT personneRepo, EnregistrerClientUE enregistrerClientUE) {
        return new LoginUE(localizeService,transactionManager,loginPT,personneRepo,enregistrerClientUE);
    }



}
