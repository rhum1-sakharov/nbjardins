package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.DemandeDeDevisRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.PersonneRoleRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RoleRepoAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.DemandeDeDevisRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.repositories.RoleRepoPT;
import usecase.clients.EnregistrerClientUE;
import usecase.devis.DemandeDeDevisUE;


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
    public EnregistrerClientUE enregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService) {
        return new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService);
    }


    @Bean
    public DemandeDeDevisUE realiserDevis(MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, DemandeDeDevisRepoPT demandeDeDevisRepo, EnregistrerClientUE enregistrerClientUE) {
        return new DemandeDeDevisUE(mailDevisService, localizeService, personneRepo, demandeDeDevisRepo, enregistrerClientUE);
    }


}
