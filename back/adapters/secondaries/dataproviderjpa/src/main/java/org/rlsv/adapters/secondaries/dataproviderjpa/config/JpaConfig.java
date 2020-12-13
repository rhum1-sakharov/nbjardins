package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.Getter;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.*;


public class JpaConfig {

    @Getter
    private static EntityManager entityManager;

    private static EntityManagerFactory entityManagerFactory;

    private static JpaConfig jpaConfig;
    public static PersistenceConfig persistenceConfig;

    public static JpaConfig getSingleton() {
        if (Objects.isNull(jpaConfig)) {
            jpaConfig = new JpaConfig();
        }
        return jpaConfig;
    }


    private JpaConfig() {

        if (Objects.isNull(persistenceConfig)) {
            throw new PersistenceException("Il faut configurer le JpaConfig.persistenceConfig avant d'appeler JpaConfig.getSingleton()");
        }

        String puName = persistenceConfig.getPersistenceUnitName();
        Map<String, String> mapPersistenceConfig = persistenceConfig.getMap();

        this.entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo(puName), mapPersistenceConfig);
        this.entityManager = this.entityManagerFactory.createEntityManager();

    }


    private static PersistenceUnitInfo persistenceUnitInfo(String persistenceUnitName) {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return persistenceUnitName;
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return null;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                return  Collections.emptyList();
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

}
