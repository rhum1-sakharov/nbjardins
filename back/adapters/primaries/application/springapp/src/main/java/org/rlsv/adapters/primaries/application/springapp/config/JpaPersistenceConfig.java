package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;


@Configuration
public class JpaPersistenceConfig {

    Environment env;


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
