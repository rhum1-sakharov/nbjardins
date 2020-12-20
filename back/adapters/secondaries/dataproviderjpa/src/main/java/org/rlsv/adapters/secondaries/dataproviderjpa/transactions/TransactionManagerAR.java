package org.rlsv.adapters.secondaries.dataproviderjpa.transactions;

import domain.transactions.DataProviderManager;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.transactions.TransactionManagerPT;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class TransactionManagerAR implements TransactionManagerPT {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionManagerAR.class);


    public static EntityManager getEntityManager(DataProviderManager dpm) {
        return (EntityManager) dpm.getManager();
    }

    @Override
    public DataProviderManager createTransactionManager() {

        JpaConfig.getSingleton();
        return new DataProviderManager(JpaConfig.entityManagerFactory.createEntityManager());
    }

    @Override
    public void begin(DataProviderManager dpm) {

        EntityManager entityManager = getEntityManager(dpm);

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
            LOG.info("begin transaction  {}", entityManager.toString());
        } else {
            LOG.info("begin transaction already active {}", entityManager.toString());
        }


    }

    @Override
    public void commit(DataProviderManager dpm) {
        EntityManager entityManager = getEntityManager(dpm);

        try {
            entityManager.getTransaction().commit();
            LOG.info("commit {}", entityManager.toString());
        } catch (RollbackException re) {
            LOG.error("commit {}", entityManager.toString(), re.getMessage());
            re.printStackTrace();
        }

    }

    @Override
    public void rollback(DataProviderManager dpm) {

        EntityManager entityManager = getEntityManager(dpm);

        entityManager.getTransaction().rollback();

        LOG.info("rollback {}", entityManager.toString());
    }

    @Override
    public void close(DataProviderManager dpm) {
        EntityManager entityManager = getEntityManager(dpm);

        entityManager.close();

        LOG.info("close {}", entityManager.toString());
    }
}
