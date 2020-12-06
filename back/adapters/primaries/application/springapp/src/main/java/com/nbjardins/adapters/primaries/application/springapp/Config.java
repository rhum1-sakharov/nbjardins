package com.nbjardins.adapters.primaries.application.springapp;

import com.nbjardins.adapters.secondaries.localization.LocalizeResourceBundleAR;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import com.nbjardins.adatpers.secondaries.mails.springmail.SpringMailDevisAR;
import domain.entities.Personne;
import domain.entities.Ville;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import usecase.devis.RealiserDevisUC;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;

@Configuration
public class Config {


    Environment env;

    public Config(Environment env) {
        this.env = env;
    }


    @Bean
    public LocalizeServicePT localizeServicePT() {
        return new LocalizeResourceBundleAR();
    }


    @Bean
    public MailDevisServicePT mailServicePT(ServerMail serverMail, LocalizeServicePT localizeServicePT) {
        return new SpringMailDevisAR(serverMail, localizeServicePT);
    }

    @Bean
    public RealiserDevisUC realiserDevis(MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT) {
        return new RealiserDevisUC(mailDevisServicePT,localizeServicePT);
    }

    @Bean
    public Personne getWorker(){

        String workerNom = env.getProperty("worker.nom");
        String workerPrenom  = env.getProperty("worker.prenom");
        String workerTelephone = env.getProperty("worker.telephone");
        String workerSociete = env.getProperty("worker.societe");
        String workerFonction = env.getProperty("worker.fonction");
        String workerVille = env.getProperty("worker.ville");
        String workerCodePostal = env.getProperty("worker.codePostal");
        String workerAdresse = env.getProperty("worker.adresse");
        String workerEmail = env.getProperty("worker.email");
        Ville ville = new Ville(workerVille,workerCodePostal);

        Personne worker = new Personne(workerNom,workerPrenom,workerTelephone,workerSociete,workerFonction,workerAdresse,ville,workerEmail);

        return worker;
    }

    @Bean
    public ServerMail serverMail() {

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
