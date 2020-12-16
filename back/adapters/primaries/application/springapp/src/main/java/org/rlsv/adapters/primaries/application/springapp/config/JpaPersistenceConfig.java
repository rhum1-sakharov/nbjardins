package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class JpaPersistenceConfig {

    Environment env;


    @Bean("persistenceConfig")
    public org.rlsv.adapters.secondaries.dataproviderjpa.config.PersistenceConfig getPersistenceConfig() {

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

        return JpaConfig.persistenceConfig;

    }

    public JpaPersistenceConfig(Environment env) {

        this.env = env;


    }


}