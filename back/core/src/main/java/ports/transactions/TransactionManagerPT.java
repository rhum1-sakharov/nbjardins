package ports.transactions;

import exceptions.CleanException;
import transactions.DataProviderManager;

public interface TransactionManagerPT {

    DataProviderManager createDataProviderManager(DataProviderManager dpm) throws CleanException;

    /**
     * Démarrer une transaction
     */
    void begin(DataProviderManager dpm) throws CleanException;

    /**
     * Valider la transaction
     */
    void commit(DataProviderManager dpm) throws CleanException;

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
