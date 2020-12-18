package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adatpers.secondaries.mails.ServerMail;
import org.rlsv.adatpers.secondaries.mails.springmail.SpringMailDevisAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;


@Configuration
public class MailConfig {

    Environment env;

    public MailConfig(Environment env) {
        this.env = env;

    }


    @Bean
    @DependsOn({"serverMail","localizeService"})
    public MailDevisServicePT mailServicePT(ServerMail serverMail, LocalizeServicePT localizeServicePT) {
        return new SpringMailDevisAR(serverMail, localizeServicePT);
    }

    @Bean(name = "serverMail")
    public ServerMail getServerMail() {

        boolean isAuth = Boolean.parseBoolean(env.getProperty("spring.mail.properties.mail.smtp.auth"));
        int port = Integer.parseInt(env.getProperty("spring.mail.port"));
        boolean enableTts = Boolean.parseBoolean(env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        boolean requiredTts = Boolean.parseBoolean(env.getProperty("spring.mail.properties.mail.smtp.starttls.required"));

        return new ServerMail(
                env.getProperty("spring.mail.host"),
                isAuth,
                port,
                env.getProperty("spring.mail.username"),
                env.getProperty("spring.mail.password"),
                enableTts,
                requiredTts,
                env.getProperty("springapp.resourceurl")
        );
    }

}
