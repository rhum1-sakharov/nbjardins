package usecases;

import lombok.Getter;
import lombok.Setter;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;

@Getter
@Setter
public class AbstractUsecase {

    protected LocalizeServicePT localizeService;
    protected TransactionManagerPT transactionManager;

    public AbstractUsecase(LocalizeServicePT localizeService, TransactionManagerPT transactionManager) {
        this.localizeService = localizeService;
        this.transactionManager = transactionManager;
    }
}
