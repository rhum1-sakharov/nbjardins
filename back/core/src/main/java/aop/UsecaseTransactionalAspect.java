package aop;

import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

public class UsecaseTransactionalAspect {

    public static void main(String[] args) {
        UsecaseTransactionalAspect ut = new UsecaseTransactionalAspect();

        DataProviderManager dpm = new DataProviderManager();

        ut.execute(dpm);
    }

    @Transactionnal
    public void execute(DataProviderManager dpm) {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }

    @Transactionnal
    public boolean execute(TransactionManagerPT tm, DataProviderManager dpm) {
        System.out.println("Executing TestTarget.yourMethodAround()");
        return true;
    }

    @Transactionnal
    public boolean execute(TransactionManagerPT tm) {
        System.out.println("Executing TestTarget.yourMethodAround()");
        return true;
    }

    @Transactionnal
    public void execute() {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }

}
