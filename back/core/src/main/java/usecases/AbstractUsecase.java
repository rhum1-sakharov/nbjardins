package usecases;

import lombok.Getter;
import lombok.Setter;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;

@Getter
@Setter
public class AbstractUsecase {

    protected LocalizeServicePT ls;
    protected TransactionManagerPT transactionManager;

    public AbstractUsecase(LocalizeServicePT ls, TransactionManagerPT transactionManager) {
        this.ls = ls;
        this.transactionManager = transactionManager;
    }
}
