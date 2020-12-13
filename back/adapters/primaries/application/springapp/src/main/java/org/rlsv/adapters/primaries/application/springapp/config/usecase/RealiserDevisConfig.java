package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import domain.models.PersonneDN;
import domain.models.VilleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.ClientRepoAR;
import org.rlsv.adapters.secondaries.localization.LocalizeResourceBundleAR;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import org.rlsv.adatpers.secondaries.mails.springmail.SpringMailDevisAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import usecase.devis.RealiserDevisUE;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;
import usecase.ports.repositories.ClientRepoPT;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RealiserDevisConfig {

    Environment env;


    public RealiserDevisConfig(Environment env) {
        this.env = env;

        Map<String, String> propertiesMap = new HashMap();
        propertiesMap.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        propertiesMap.put("javax.persistence.jdbc.url", env.getProperty("javax.persistence.jdbc.url"));
        propertiesMap.put("javax.persistence.jdbc.user", env.getProperty("javax.persistence.jdbc.user"));
        propertiesMap.put("javax.persistence.jdbc.password", env.getProperty("javax.persistence.jdbc.password"));
        propertiesMap.put("javax.persistence.jdbc.driver", env.getProperty("javax.persistence.jdbc.driver"));
        propertiesMap.put("hibernate.hikari.connectionTimeout", env.getProperty("hibernate.hikari.connectionTimeout"));
        propertiesMap.put("hibernate.hikari.minimumIdle", env.getProperty("hibernate.hikari.minimumIdle"));
        propertiesMap.put("hibernate.hikari.maximumPoolSize", env.getProperty("hibernate.hikari.maximumPoolSize"));
        propertiesMap.put("hibernate.hikari.idleTimeout", env.getProperty("hibernate.hikari.idleTimeout"));
        JpaConfig.persistenceConfig = new org.rlsv.adapters.secondaries.dataproviderjpa.config.PersistenceConfig("PERSISTENCE_UNIT_NB_JARDINS", propertiesMap);


    }

    @Bean
    public LocalizeServicePT localizeServicePT() {
        return new LocalizeResourceBundleAR();
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

    @Bean
    public ClientRepoPT clientRepoPT() {
        return new ClientRepoAR();
    }


    @Bean
    public MailDevisServicePT mailServicePT(ServerMail serverMail, LocalizeServicePT localizeServicePT) {
        return new SpringMailDevisAR(serverMail, localizeServicePT);
    }

    @Bean
    public RealiserDevisUE realiserDevis(MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT, ClientRepoPT clientRepoPT) {
        return new RealiserDevisUE(mailDevisServicePT, localizeServicePT, clientRepoPT);
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

}
