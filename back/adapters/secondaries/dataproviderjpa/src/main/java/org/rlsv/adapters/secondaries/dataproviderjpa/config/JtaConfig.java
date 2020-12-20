package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.ClientRepoAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import java.util.Objects;

public class JtaConfig {

    public static final String DATASOURCE_NAME = "myDS";
    public static final String PERSISTENCE_UNIT_RLSV = "PERSISTENCE_UNIT_RLSV";

    private static final Logger LOG = LoggerFactory.getLogger(ClientRepoAR.class);
    protected final PoolingDataSource datasource;
    protected final Context context = new InitialContext();
    private static EntityManagerFactory emf;

    public static DatabaseConnectionConfig databaseConnectionConfig;
    private static volatile JtaConfig instance;
    private static Object mutex = new Object();

    public static JtaConfig getInstance() throws Exception {
        JtaConfig result = instance;
        if (Objects.isNull(result)) {
            synchronized (mutex) {
                result = instance;
                if (Objects.isNull(result)) {
                    instance = result = new JtaConfig(databaseConnectionConfig);
                }
            }
        }

        return result;
    }


    private JtaConfig(DatabaseConnectionConfig databaseConnectionConfig) throws Exception {

        LOG.info("Starting database connection pool");

        LOG.info("Setting stable unique identifier for transaction recovery");
        TransactionManagerServices.getConfiguration().setServerId("myServer1234");

        LOG.info("Disabling JMX binding of manager in unit tests");
        TransactionManagerServices.getConfiguration().setDisableJmx(true);

        LOG.info("Disabling transaction logging for unit tests");
        TransactionManagerServices.getConfiguration().setJournal("null");

        LOG.info("Disabling warnings when the database isn't accessed in a transaction");
        TransactionManagerServices.getConfiguration().setWarnAboutZeroResourceTransaction(false);

        LOG.info("Creating connection pool");
        datasource = new PoolingDataSource();
        datasource.setUniqueName(DATASOURCE_NAME);
        datasource.setMinPoolSize(1);
        datasource.setMaxPoolSize(5);
        datasource.setPreparedStatementCacheSize(10);

        // Our locking/versioning tests assume READ COMMITTED transaction
        // isolation. This is not the default on MySQL InnoDB, so we set
        // it here explicitly.
        datasource.setIsolationLevel("READ_COMMITTED");

        // Hibernate's SQL schema generator calls connection.setAutoCommit(true)
        // and we use auto-commit mode when the EntityManager is in suspended
        // mode and not joined with a transaction.
        datasource.setAllowLocalTransactions(true);


        LOG.info("Setting up database connection: ");
        datasource.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        datasource.getDriverProperties().put("url", databaseConnectionConfig.getUrl());
        datasource.getDriverProperties().put("driverClassName", databaseConnectionConfig.getDriver());
        datasource.getDriverProperties().put("user", databaseConnectionConfig.getUser());
        datasource.getDriverProperties().put("password", databaseConnectionConfig.getPassword());

        LOG.info("Initializing transaction and resource management");
        datasource.init();

        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_RLSV);
    }

    public Context getNamingContext() {
        return context;
    }

    public UserTransaction getUserTransaction() {
        try {
            return (UserTransaction) getNamingContext()
                    .lookup("java:comp/UserTransaction");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DataSource getDataSource() {
        try {
            return (DataSource) getNamingContext().lookup(DATASOURCE_NAME);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void rollback() {
        UserTransaction tx = getUserTransaction();
        try {
            if (tx.getStatus() == Status.STATUS_ACTIVE ||
                    tx.getStatus() == Status.STATUS_MARKED_ROLLBACK)
                tx.rollback();
        } catch (Exception ex) {
            System.err.println("Rollback of transaction failed, trace follows!");
            ex.printStackTrace(System.err);
        }
    }

    public void stop() throws Exception {
        LOG.info("Stopping database connection pool");
        datasource.close();
        TransactionManagerServices.getTransactionManager().shutdown();
    }

    public EntityManager createEntityManager() {

        return emf.createEntityManager();
    }
}
