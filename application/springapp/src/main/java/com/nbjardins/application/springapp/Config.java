package com.nbjardins.application.springapp;

import com.nbjardins.config.SpringConfig;
import com.nbjardins.domain.entities.ServerMail;
import com.nbjardins.usecase.RealiserDevis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Config {

    private final SpringConfig config = new SpringConfig();

    Environment env;

    public Config(Environment env) {
        this.env = env;
    }

    @Bean
    public RealiserDevis realiserDevis() {
        return config.realiserDevis();
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
