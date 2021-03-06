package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis.DevisRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.PersonneRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.ArtisanRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.banques.ArtisanBanqueRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.clients.ClientRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.roles.PersonneRoleRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.roles.RoleRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.taxes.TaxeRepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import ports.repositories.*;
import ports.transactions.TransactionManagerPT;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;


@Configuration
public class JpaPersistenceConfig {

    Environment env;

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
    public DevisRepoPT demandeDeDevisRepoPT(ArtisanRepoPT artisanRepo, ClientRepoPT clientRepo) {
        return new DevisRepoAR(artisanRepo, clientRepo);
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

    @Bean("databaseConnectionConfig")
    public DatabaseConnectionConfig getPersistenceConfig() {

        String user = env.getProperty("javax.persistence.jdbc.user");
        String password = env.getProperty("javax.persistence.jdbc.password");
        String url = env.getProperty("javax.persistence.jdbc.url");
        String driver = env.getProperty("javax.persistence.jdbc.driver");

        JtaConfig.databaseConnectionConfig = new DatabaseConnectionConfig(user, password, url, driver, PERSISTENCE_UNIT_RLSV);

        return JtaConfig.databaseConnectionConfig;

    }

    public JpaPersistenceConfig(Environment env) {

        this.env = env;


    }


}
