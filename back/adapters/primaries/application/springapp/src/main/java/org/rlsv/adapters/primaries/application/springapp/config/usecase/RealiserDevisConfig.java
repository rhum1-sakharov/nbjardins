package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.DemandeDeDevisRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRoleRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RoleRepoAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import usecase.devis.DemandeDeDevisUE;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;
import usecase.ports.repositories.DemandeDeDevisRepoPT;
import usecase.ports.repositories.PersonneRepoPT;
import usecase.ports.repositories.PersonneRoleRepoPT;
import usecase.ports.repositories.RoleRepoPT;


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
    public RoleRepoPT roleRepoPT() {
        return new RoleRepoAR();
    }

    @Bean
    @DependsOn("persistenceConfig")
    public DemandeDeDevisRepoPT demandeDeDevisRepoPT(PersonneRepoPT personneRepo) {
        return new DemandeDeDevisRepoAR(personneRepo);
    }


    @Bean
    @DependsOn("persistenceConfig")
    public PersonneRoleRepoPT personneRoleRepoPT(PersonneRepoPT personneRepo, RoleRepoPT roleRepo) {
        return new PersonneRoleRepoAR(personneRepo, roleRepo);
    }


    @Bean
    public DemandeDeDevisUE realiserDevis(MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, DemandeDeDevisRepoPT demandeDeDevisRepo) {
        return new DemandeDeDevisUE(mailDevisService, localizeService, personneRepo, personneRoleRepo,demandeDeDevisRepo);
    }


}
