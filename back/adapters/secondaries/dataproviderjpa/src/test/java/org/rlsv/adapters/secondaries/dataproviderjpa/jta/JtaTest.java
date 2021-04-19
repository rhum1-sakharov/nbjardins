package org.rlsv.adapters.secondaries.dataproviderjpa.jta;

import org.junit.Before;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.roles.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;

public class JtaTest {


    @Before
    public void setUp() {

        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/unit_tests_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";

        JtaConfig.databaseConnectionConfig = new DatabaseConnectionConfig(user, password, url, driver, PERSISTENCE_UNIT_RLSV);
    }


    @Test
    public void testBitronix() throws Exception {


        JtaConfig tm = JtaConfig.getInstance();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_RLSV);

        UserTransaction tx = tm.getUserTransaction();


        EntityManager em = emf.createEntityManager();

        try {
            tx.begin();

            Role role = new Role();
            role.setNom("TEST_ROLE");
            role.setDescription("test");
            em.persist(role);

            Personne personne = new Personne();
            personne.setNom("test");
            personne.setEmail("email2");
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
