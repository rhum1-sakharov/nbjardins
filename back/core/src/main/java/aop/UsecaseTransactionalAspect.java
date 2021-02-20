package aop;

import transactions.DataProviderManager;

public class UsecaseTransactionalAspect  {



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
    public void execute() {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }

}
