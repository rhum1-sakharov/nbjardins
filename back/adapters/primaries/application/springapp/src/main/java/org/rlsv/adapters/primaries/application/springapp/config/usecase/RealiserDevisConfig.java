package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import domain.models.PersonneDN;
import domain.models.VilleDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRoleRepoAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import usecase.devis.DemandeDeDevisUE;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;
import usecase.ports.repositories.PersonneRepoPT;
import usecase.ports.repositories.PersonneRoleRepoPT;


@Configuration
public class RealiserDevisConfig {

    Environment env;


    public RealiserDevisConfig(Environment env) {
        this.env = env;
    }


    @Bean
    @DependsOn("persistenceConfig")
    public PersonneRepoPT personneRepoPT() {
        return new PersonneRepoAR();
    }


    @Bean
    @DependsOn("persistenceConfig")
    public PersonneRoleRepoPT personneRoleRepoPT() {
        return new PersonneRoleRepoAR();
    }



    @Bean
    public DemandeDeDevisUE realiserDevis(MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT, PersonneRepoPT personneRepoPT, PersonneRoleRepoPT personneRoleRepoPT) {
        return new DemandeDeDevisUE(mailDevisServicePT, localizeServicePT, personneRepoPT,personneRoleRepoPT);
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
