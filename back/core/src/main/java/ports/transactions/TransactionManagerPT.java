package ports.transactions;

import transactions.DataProviderManager;

public interface TransactionManagerPT {

    DataProviderManager createDataProviderManager(DataProviderManager dpm) throws Exception;

    /**
     * Démarrer une transaction
     */
    void begin(DataProviderManager dpm) throws Exception;

    /**
     * Valider la transaction
     */
    void commit(DataProviderManager dpm) throws Exception;

    /**
     * Annuler la transaction
     */
    void rollback(DataProviderManager dpm) ;

    /**
     * Fermer le DataProviderManager
     *
     * @param dpm
     */
    void close(DataProviderManager dpm);

}
