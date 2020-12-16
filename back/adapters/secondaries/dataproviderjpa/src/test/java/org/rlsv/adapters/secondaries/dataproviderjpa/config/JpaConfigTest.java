package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class JpaConfigTest {

    EntityManager entityManager;

    @Before
    public void setUp() {

        Map<String, String> propertiesMap = new HashMap();
        propertiesMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        propertiesMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/test_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        propertiesMap.put("javax.persistence.jdbc.user", "root");
        propertiesMap.put("javax.persistence.jdbc.password", "");
        propertiesMap.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        propertiesMap.put("hibernate.hikari.connectionTimeout", "20000");
        propertiesMap.put("hibernate.hikari.minimumIdle", "10");
        propertiesMap.put("hibernate.hikari.maximumPoolSize", "20");
        propertiesMap.put("hibernate.hikari.idleTimeout", "300000");

        JpaConfig.persistenceConfig = new PersistenceConfig("PERSISTENCE_UNIT_NB_JARDINS", propertiesMap);
        entityManager = JpaConfig.getSingleton().getEntityManager();

    }

    @Test
    public void entityManager_should_not_be_null() {
        Assertions.assertThat(Objects.nonNull(entityManager)).isTrue();
    }




}