package org.rlsv.adapters.primaries.application.springapp.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

//    private DataSource getLiquibaseDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.driver"));
//        dataSourceBuilder.url(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.url"));
//        dataSourceBuilder.username(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.user"));
//        dataSourceBuilder.password(JpaConfig.persistenceConfig.getMap().get("javax.persistence.jdbc.password"));
//        return dataSourceBuilder.build();
//    }
//
//
//    @Bean
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog("classpath:liquibase/liquibase-changelog.xml");
//        liquibase.setDataSource(getLiquibaseDataSource());
//        return liquibase;
//    }
}
