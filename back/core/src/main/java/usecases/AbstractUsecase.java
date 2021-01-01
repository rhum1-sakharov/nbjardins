package usecases;

import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;

public class AbstractUsecase {

    protected LocalizeServicePT localizeService;
    protected TransactionManagerPT transactionManager;

    public AbstractUsecase(LocalizeServicePT localizeService, TransactionManagerPT transactionManager) {
        this.localizeService = localizeService;
        this.transactionManager = transactionManager;
    }
}
