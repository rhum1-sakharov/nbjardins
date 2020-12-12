package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;


public class JpaConfig {

    @Getter
    private EntityManager entityManager;

    private EntityManagerFactory entityManagerFactory;

    public static JpaConfig jpaConfig;
    public static PropertiesConfig propertiesConfig;

    public static JpaConfig getSingleton() {
        if (Objects.isNull(jpaConfig)) {
            jpaConfig = new JpaConfig();
        }
        return jpaConfig;
    }

    private JpaConfig() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(propertiesConfig.getPersistenceUnitName(),propertiesConfig.getMap());
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

}
