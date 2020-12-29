package org.rlsv.adapters.primaries.application.springapp.config.usecase;

import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.*;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.*;
import ports.transactions.TransactionManagerPT;
import usecase.clients.EnregistrerClientUE;
import usecase.devis.DemandeDeDevisUE;
import usecase.uniquecode.UniqueCodeUE;


@Configuration
public class RealiserDevisConfig {

    Environment env;

    public RealiserDevisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public PersonneRepoPT personneRepoPT() {
        return new PersonneRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public RoleRepoPT roleRepoPT() {
        return new RoleRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public DevisRepoPT demandeDeDevisRepoPT(PersonneRepoPT personneRepo) {
        return new DevisRepoAR(personneRepo);
    }


    @Bean
    @DependsOn("databaseConnectionConfig")
    public PersonneRoleRepoPT personneRoleRepoPT(PersonneRepoPT personneRepo, RoleRepoPT roleRepo) {
        return new PersonneRoleRepoAR(personneRepo, roleRepo);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TransactionManagerPT transactionManagerPT() {
        return new TransactionManagerAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ClientRepoPT clientRepoPT() {
        return new ClientRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TaxeRepoPT taxeRepoPT() {
        return new TaxeRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanBanqueRepoPT artisanBanqueRepoPT() {
        return new ArtisanBanqueRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ConditionDeReglementRepoPT conditionDeReglementRepoPT() {
        return new ConditionDeReglementRepoAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanRepoPT artisanRepoPT() {
        return new ArtisanRepoAR();
    }

    @Bean
    public UniqueCodeUE uniqueCodeUE(LocalizeServicePT localizeService,  TransactionManagerPT transactionManager, DevisRepoPT devisRepo){
        return  new UniqueCodeUE(localizeService,transactionManager,devisRepo);
    }

    @Bean
    public EnregistrerClientUE enregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService, ClientRepoPT clientRepo, TransactionManagerPT transactionManager) {
        return new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService, clientRepo, transactionManager);
    }


    @Bean
    public DemandeDeDevisUE realiserDevis(MailDevisServicePT mailDevisService,
                                          LocalizeServicePT localizeService,
                                          PersonneRepoPT personneRepo,
                                          DevisRepoPT demandeDeDevisRepo,
                                          EnregistrerClientUE enregistrerClientUE,
                                          TransactionManagerPT transactionManager,
                                          TaxeRepoPT taxeRepo,
                                          UniqueCodeUE uniqueCodeUE,
                                          ArtisanBanqueRepoPT artisanBanqueRepo,
                                          ConditionDeReglementRepoPT conditionDeReglementRepo,
                                          ArtisanRepoPT artisanRepo
                                          ) {
        return new DemandeDeDevisUE(mailDevisService,
                localizeService,
                personneRepo,
                demandeDeDevisRepo,
                enregistrerClientUE,
                transactionManager,
                taxeRepo,
                uniqueCodeUE,
                artisanBanqueRepo,
                conditionDeReglementRepo,
                artisanRepo
                );
    }


}
