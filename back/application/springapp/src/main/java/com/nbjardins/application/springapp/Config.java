package com.nbjardins.application.springapp;

import com.nbjardins.adatpers.secondaries.mails.AJavaMail;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import usecase.RealiserDevis;
import usecase.ports.PMailService;

@Configuration
public class Config {


    Environment env;

    public Config(Environment env) {
        this.env = env;
    }

    @Bean
    public PMailService pMailService(ServerMail serverMail) {
        return new AJavaMail(serverMail);
    }

    @Bean
    public RealiserDevis realiserDevis(PMailService pMailService) {
        return new RealiserDevis(pMailService);
    }

    @Bean
    public ServerMail serverMail() {

        boolean isAuth = Boolean.parseBoolean(env.getProperty("mail.properties.mail.smtp.auth"));
        int port = Integer.parseInt(env.getProperty("mail.port"));
        boolean enableTts = Boolean.parseBoolean(env.getProperty("mail.properties.mail.smtp.starttls.enable"));
        boolean requiredTts = Boolean.parseBoolean(env.getProperty("mail.properties.mail.smtp.starttls.required"));

        return new ServerMail(
                env.getProperty("mail.host"),
                isAuth,
                port,
                env.getProperty("mail.username"),
                env.getProperty("mail.password"),
                enableTts,
                requiredTts);
    }

}
