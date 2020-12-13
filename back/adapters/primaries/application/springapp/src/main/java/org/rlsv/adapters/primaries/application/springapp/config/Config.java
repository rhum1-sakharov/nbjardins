package org.rlsv.adapters.primaries.application.springapp.config;

import domain.models.PersonneDN;
import domain.models.VilleDN;
import org.rlsv.adapters.secondaries.localization.LocalizeResourceBundleAR;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import org.rlsv.adatpers.secondaries.mails.springmail.SpringMailDevisAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import usecase.devis.RealiserDevisUE;
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

//    @Bean
//    public ClientRepoPT clientRepoPT() {
//        return null;
//    }


    @Bean
    public MailDevisServicePT mailServicePT(ServerMail serverMail, LocalizeServicePT localizeServicePT) {
        return new SpringMailDevisAR(serverMail, localizeServicePT);
    }

    @Bean
    public RealiserDevisUE realiserDevis(MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT) {
        return new RealiserDevisUE(mailDevisServicePT, localizeServicePT);
    }

    @Bean
    public PersonneDN getWorker() {

        String workerNom = env.getProperty("worker.nom");
        String workerPrenom = env.getProperty("worker.prenom");
        String workerTelephone = env.getProperty("worker.telephone");
        String workerSociete = env.getProperty("worker.societe");
        String workerFonction = env.getProperty("worker.fonction");
        String workerVille = env.getProperty("worker.ville");
        String workerCodePostal = env.getProperty("worker.codePostal");
        String workerAdresse = env.getProperty("worker.adresse");
        String workerEmail = env.getProperty("worker.email");
        VilleDN villeDN = new VilleDN(workerVille, workerCodePostal);

        PersonneDN worker = new PersonneDN(workerNom, workerPrenom, workerTelephone, workerSociete, workerFonction, workerAdresse, villeDN, workerEmail);

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
