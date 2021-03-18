package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis.DevisRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.PersonneRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.ArtisanRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.banques.ArtisanBanqueRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.artisans.options.ArtisanOptionRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.clients.ClientRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.roles.PersonneRoleRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.roles.RoleRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.taxes.TaxeRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import ports.repositories.devis.DevisRepoPT;
import ports.repositories.personnes.PersonneRepoPT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.repositories.personnes.roles.PersonneRoleRepoPT;
import ports.repositories.referentiel.conditions.reglements.ConditionDeReglementRepoPT;
import ports.repositories.referentiel.roles.RoleRepoPT;
import ports.repositories.referentiel.taxes.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;


@Configuration
public class JpaPersistenceConfig {

    Environment env;

    @Bean
    @DependsOn("databaseConnectionConfig")
    public PersonneRepoPT personneRepoPT() {
        return new PersonneRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public RoleRepoPT roleRepoPT() {
        return new RoleRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public DevisRepoPT demandeDeDevisRepoPT(ArtisanRepoPT artisanRepo, ClientRepoPT clientRepo) {
        return new DevisRepoJpa(artisanRepo, clientRepo);
    }


    @Bean
    @DependsOn("databaseConnectionConfig")
    public PersonneRoleRepoPT personneRoleRepoPT(PersonneRepoPT personneRepo, RoleRepoPT roleRepo) {
        return new PersonneRoleRepoJpa(personneRepo, roleRepo);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TransactionManagerPT transactionManagerPT() {
        return new TransactionManagerAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ClientRepoPT clientRepoPT() {
        return new ClientRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TaxeRepoPT taxeRepoPT() {
        return new TaxeRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanBanqueRepoPT artisanBanqueRepoPT() {
        return new ArtisanBanqueRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ConditionDeReglementRepoPT conditionDeReglementRepoPT() {
        return new ConditionDeReglementRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanRepoPT artisanRepoPT() {
        return new ArtisanRepoJpa();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanOptionRepoPT artisanOptionRepoPT() {
        return new ArtisanOptionRepoJpa();
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
