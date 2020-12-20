package org.rlsv.adapters.secondaries.dataproviderjpa.jta;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.ClientRepoAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

public class TransactionManagerSetup {

    public static final String DATASOURCE_NAME = "myDS";
    private static final Logger logger = LoggerFactory.getLogger(ClientRepoAR.class);
    protected final PoolingDataSource datasource;
    public final DatabaseProduct databaseProduct;
    protected final Context context = new InitialContext();

    public TransactionManagerSetup(DatabaseProduct databaseProduct) throws Exception {
        this(databaseProduct, null);
    }

    public TransactionManagerSetup(DatabaseProduct databaseProduct,
                                   String connectionURL) throws Exception {

        logger.info("Starting database connection pool");

        logger.info("Setting stable unique identifier for transaction recovery");
        TransactionManagerServices.getConfiguration().setServerId("myServer1234");

        logger.info("Disabling JMX binding of manager in unit tests");
        //TransactionManagerServices.getConfiguration().setDisableJmx(true);

        logger.info("Disabling transaction logging for unit tests");
        //TransactionManagerServices.getConfiguration().setJournal("null");

        logger.info("Disabling warnings when the database isn't accessed in a transaction");
        TransactionManagerServices.getConfiguration().setWarnAboutZeroResourceTransaction(false);

        logger.info("Creating connection pool");
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

        logger.info("Setting up database connection: " + databaseProduct);
        this.databaseProduct = databaseProduct;
        databaseProduct.configuration.configure(datasource, connectionURL);

        logger.info("Initializing transaction and resource management");
        datasource.init();
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
        logger.info("Stopping database connection pool");
        datasource.close();
        TransactionManagerServices.getTransactionManager().shutdown();
    }

}
