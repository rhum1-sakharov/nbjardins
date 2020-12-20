package ports.transactions;

import domain.transactions.DataProviderManager;

public interface TransactionManagerPT {

   DataProviderManager createTransactionManager();

    /**
     * Démarrer une transaction
     */
    void begin(DataProviderManager dpm);

    /**
     * Valider la transaction
     */
    void commit(DataProviderManager dpm);

    /**
     * Annuler la transaction
     */
    void rollback(DataProviderManager dpm);

 /**
  * Fermer le DataProviderManager
  * @param dpm
  */
 void close(DataProviderManager dpm);

}
