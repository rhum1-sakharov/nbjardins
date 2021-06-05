package aop;

import annotations.Transactional;
import transactions.DataProviderManager;

public class UsecaseTransactionalAspect  {



    public static void main(String[] args) {
        UsecaseTransactionalAspect ut = new UsecaseTransactionalAspect();


        DataProviderManager dpm = new DataProviderManager();

        ut.execute(dpm);
    }

    @Transactional
    public void execute(DataProviderManager dpm) {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }



    @Transactional
    public void execute() {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }

}
