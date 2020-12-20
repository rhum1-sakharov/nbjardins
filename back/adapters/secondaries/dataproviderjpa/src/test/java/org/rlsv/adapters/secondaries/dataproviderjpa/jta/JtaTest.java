package org.rlsv.adapters.secondaries.dataproviderjpa.jta;

import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.PersistenceConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.Map;

public class JtaTest {


    public static void setUp() {

        Map<String, String> propertiesMap = new HashMap();
        propertiesMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        propertiesMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/unit_tests_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        propertiesMap.put("javax.persistence.jdbc.user", "root");
        propertiesMap.put("javax.persistence.jdbc.password", "");
        propertiesMap.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        propertiesMap.put("hibernate.hikari.connectionTimeout", "20000");
        propertiesMap.put("hibernate.hikari.minimumIdle", "10");
        propertiesMap.put("hibernate.hikari.maximumPoolSize", "20");
        propertiesMap.put("hibernate.hikari.idleTimeout", "300000");

        JpaConfig.persistenceConfig = new PersistenceConfig("PERSISTENCE_UNIT_NB_JARDINS", propertiesMap);

    }

    @Test
    public void testBitronix() throws Exception {


        TransactionManagerSetup tm = new TransactionManagerSetup(DatabaseProduct.MYSQL, "jdbc:mysql://localhost:3306/unit_tests_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HelloWorldPU");

        UserTransaction tx = tm.getUserTransaction();


        EntityManager em = emf.createEntityManager();

        try {
            tx.begin();

            Personne personne = new Personne();
            personne.setNom("test");
            personne.setEmail("email");
            personne.setPrenom("prenom");

            em.persist(personne);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (tx.getStatus() == Status.STATUS_ACTIVE || tx.getStatus() == Status.STATUS_MARKED_ROLLBACK) {

                    System.out.println("tryng to roolback");
                    tx.rollback();
                    System.out.println("roolback ok");

                }
            } catch (Exception rbEx) {
                System.out.println("erreur roolback");
                rbEx.printStackTrace();
            }
        }

        em.close();

    }

}
