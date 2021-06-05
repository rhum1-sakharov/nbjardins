package aop;

import annotations.RvlTransactional;
import transactions.DataProviderManager;

public class UsecaseTransactionalAspect  {



    public static void main(String[] args) {
        UsecaseTransactionalAspect ut = new UsecaseTransactionalAspect();


        DataProviderManager dpm = new DataProviderManager();

        ut.execute(dpm);
    }

    @RvlTransactional
    public void execute(DataProviderManager dpm) {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }



    @RvlTransactional
    public void execute() {
        System.out.println("Executing TestTarget.yourMethodAround()");
    }

}
