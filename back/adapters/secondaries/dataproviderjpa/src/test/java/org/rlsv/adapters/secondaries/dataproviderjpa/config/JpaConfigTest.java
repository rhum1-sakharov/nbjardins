package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class JpaConfigTest {

    EntityManager entityManager;

    @BeforeEach
    public void setUp() {

        Map<String,String> propertiesMap = new HashMap();
        propertiesMap.put("dialect","org.hibernate.dialect.MySQL8Dialect");
        propertiesMap.put("connection.url","jdbc:mysql://localhost:3306/nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        propertiesMap.put("connection.username","root");
        propertiesMap.put("connection.password","");
        propertiesMap.put("connection.driver_class","com.mysql.cj.jdbc.Driver");


        JpaConfig.propertiesConfig = new PropertiesConfig("puNbJardins",propertiesMap);
        this.entityManager = JpaConfig.getSingleton().getEntityManager();
    }

    @Test
    void entityManager_should_not_be_null() {
        Assertions.assertThat(Objects.nonNull(this.entityManager)).isTrue();
    }


}