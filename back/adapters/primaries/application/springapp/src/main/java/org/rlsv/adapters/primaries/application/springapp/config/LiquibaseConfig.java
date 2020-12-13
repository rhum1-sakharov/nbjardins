package org.rlsv.adapters.primaries.application.springapp.config;

import liquibase.integration.spring.SpringLiquibase;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;


@Configuration
public class LiquibaseConfig {

    private DataSource getLiquibaseDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.driver"));
        dataSourceBuilder.url(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.url"));
        dataSourceBuilder.username(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.user"));
        dataSourceBuilder.password(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.password"));
        return dataSourceBuilder.build();
    }


    @Bean
    @DependsOn("persistenceConfig")
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/liquibase-changelog.xml");
        liquibase.setDataSource(getLiquibaseDataSource());
        return liquibase;
    }
}
