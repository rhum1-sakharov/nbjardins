package org.rlsv.adapters.primaries.application.springapp.config;

import liquibase.integration.spring.SpringLiquibase;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;


@Configuration
public class LiquibaseConfig {

    private DataSource getLiquibaseDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(JtaConfig.databaseConnectionConfig.getDriver());
        dataSourceBuilder.url(JtaConfig.databaseConnectionConfig.getUrl());
        dataSourceBuilder.username(JtaConfig.databaseConnectionConfig.getUser());
        dataSourceBuilder.password(JtaConfig.databaseConnectionConfig.getPassword());
        return dataSourceBuilder.build();
    }


    @Bean
    @DependsOn("databaseConnectionConfig")
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/liquibase-changelog.xml");
        liquibase.setDataSource(getLiquibaseDataSource());
        return liquibase;
    }
}
