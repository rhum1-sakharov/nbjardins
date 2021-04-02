package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis.DevisRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis.options.DevisOptionRepoJpa;
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
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.repositories.devis.options.DevisOptionRepoPT;
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
    public PersonneRepoPT personneRepoPT(LocalizeServicePT ls) {
        return new PersonneRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public RoleRepoPT roleRepoPT(LocalizeServicePT ls) {
        return new RoleRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public DevisRepoPT demandeDeDevisRepoPT(LocalizeServicePT ls, ArtisanRepoPT artisanRepo, ClientRepoPT clientRepo) {
        return new DevisRepoJpa(ls, artisanRepo, clientRepo);
    }


    @Bean
    @DependsOn("databaseConnectionConfig")
    public PersonneRoleRepoPT personneRoleRepoPT(LocalizeServicePT ls, PersonneRepoPT personneRepo, RoleRepoPT roleRepo) {
        return new PersonneRoleRepoJpa(ls, personneRepo, roleRepo);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TransactionManagerPT transactionManagerPT(LocalizeServicePT ls) {
        return new TransactionManagerAR();
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ClientRepoPT clientRepoPT(LocalizeServicePT ls) {
        return new ClientRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public TaxeRepoPT taxeRepoPT(LocalizeServicePT ls) {
        return new TaxeRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanBanqueRepoPT artisanBanqueRepoPT(LocalizeServicePT ls) {
        return new ArtisanBanqueRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ConditionDeReglementRepoPT conditionDeReglementRepoPT(LocalizeServicePT ls) {
        return new ConditionDeReglementRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanRepoPT artisanRepoPT(LocalizeServicePT ls) {
        return new ArtisanRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public ArtisanOptionRepoPT artisanOptionRepoPT(LocalizeServicePT ls) {
        return new ArtisanOptionRepoJpa(ls);
    }

    @Bean
    @DependsOn("databaseConnectionConfig")
    public DevisOptionRepoPT devisOptionRepoPT(LocalizeServicePT ls) {
        return new DevisOptionRepoJpa(ls);
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
