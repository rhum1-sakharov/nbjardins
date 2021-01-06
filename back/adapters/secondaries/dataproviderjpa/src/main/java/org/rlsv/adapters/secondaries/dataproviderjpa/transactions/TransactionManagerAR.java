package org.rlsv.adapters.secondaries.dataproviderjpa.transactions;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import java.util.Objects;

public class TransactionManagerAR implements TransactionManagerPT {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionManagerAR.class);


    public static UserTransaction getUserTransaction(DataProviderManager dpm) {
        return (UserTransaction) dpm.getTransactionManager();
    }

    @Override
    public DataProviderManager createDataProviderManager(DataProviderManager dpm) throws Exception {

        JtaConfig jtaConfig = JtaConfig.getInstance();
        EntityManager em = jtaConfig.createEntityManager();
        boolean nestedTransaction = false;

        UserTransaction tx;
        if (Objects.isNull(dpm)) {
            tx = jtaConfig.getUserTransaction();
        } else {
            tx = (UserTransaction) dpm.getTransactionManager();
            nestedTransaction = true;
        }

        dpm = new DataProviderManager(em, tx, nestedTransaction);

        return dpm;
    }

    @Override
    public void begin(DataProviderManager dpm) throws Exception {

        if (!dpm.isNestedTransaction()) {

            UserTransaction tx = getUserTransaction(dpm);

            if (tx.getStatus() != Status.STATUS_ACTIVE) {
                tx.begin();
            }

            EntityManager entityManager = PersistenceUtils.getEntityManager(dpm);

            LOG.debug("begin jta transaction for entityManager {} {}", entityManager.toString(), tx.toString());
        } else {
            LOG.debug("begin, jta transaction is already started");
        }


    }

    @Override
    public void commit(DataProviderManager dpm) throws Exception {

        if (!dpm.isNestedTransaction()) {

            UserTransaction tx = getUserTransaction(dpm);
            EntityManager entityManager = PersistenceUtils.getEntityManager(dpm);

            LOG.debug("trying to commit jta transaction for entityManager {} {}", entityManager.toString(), tx.toString());

            tx.commit();


            LOG.debug("jta transaction commited for entityManager {}", entityManager.toString());
        } else {
            LOG.debug("commit, jta transaction is already started");
        }

    }


    @Override
    public void rollback(DataProviderManager dpm) {

        try {
            UserTransaction tx = getUserTransaction(dpm);

            if (tx.getStatus() == Status.STATUS_ACTIVE ||
                    tx.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
                tx.rollback();
                LOG.warn("Rollback executed for transaction {}",tx.toString());
            }

        } catch (Exception ex) {
            System.err.println("Rollback of transaction failed, trace follows!");
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void close(DataProviderManager dpm) {
        EntityManager entityManager = PersistenceUtils.getEntityManager(dpm);

        if (Objects.nonNull(entityManager)) {
            entityManager.close();
            LOG.debug("close {}", entityManager.toString());
        }


    }
}
