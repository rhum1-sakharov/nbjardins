package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.Getter;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.reflections.Reflections;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class JpaConfig {

    private final static String PACKAGE_NAME_ENTITIES = "org.rlsv.adapters.secondaries.dataproviderjpa.entities";

    @Getter
    private static EntityManager entityManager;

    public static EntityManagerFactory entityManagerFactory;
    public static PersistenceConfig persistenceConfig;
    private static volatile JpaConfig jpaConfig;
    private static Object mutex = new Object();


    /**
     * https://www.journaldev.com/171/thread-safety-in-java-singleton-classes
     * https://medium.com/@cancerian0684/singleton-design-pattern-and-how-to-make-it-thread-safe-b207c0e7e368
     *
     * @return singleton
     */
    public static JpaConfig getSingleton() {
        JpaConfig localRef = jpaConfig;
        if (Objects.isNull(localRef)) {
            synchronized (mutex) {
                localRef = jpaConfig;
                if (localRef == null) {
                    jpaConfig = new JpaConfig();
                }
            }
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


    public static PersistenceUnitInfo persistenceUnitInfo(String persistenceUnitName) {
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
                return Collections.emptyList();
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {

                Reflections reflections = new Reflections(PACKAGE_NAME_ENTITIES);

                Set<Class<?>> clazzList = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);

                List<String> managedList = clazzList.stream()
                        .map(clazz -> clazz.getCanonicalName())
                        .collect(Collectors.toList());

                return managedList;

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
