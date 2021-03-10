package aop;

import exceptions.TechnicalException;
import models.Precondition;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

@Aspect
public class TransactionalAspect {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionalAspect.class);

    //Defines a pointcut where the @YourAnnotation exists
    //And combines that with a catch all pointcut with the scope of execution
    @Around("@annotation(aop.Transactional) && execution(* *(..))")
    //ProceedingJointPoint = the reference of the call to the method.
    //Difference between ProceedingJointPoint and JointPoint is that a JointPoint can't be continued(proceeded)
    //A ProceedingJointPoint can be continued(proceeded) and is needed for a Around advice
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        MethodSignature ms = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Class<?>[] parameterTypes = ms.getMethod().getParameterTypes();

        //Default Object that we can use to return to the consumer
        Object returnObject = null;
        DataProviderManager dpm = null;
        TransactionManagerPT tm = null;

        try {
            LOG.debug("TransactionalAspect's aroundAdvice's body is now executed Before yourMethodAround is called.");

            tm = getInstanceOfTm(joinPoint);
            int indexDpmArg = Precondition.getIndexClassType(parameterTypes, DataProviderManager.class);

            Precondition.validate(
                    Precondition.init("no transaction manager provided. In order to use @Transactional annotation, your method class needs to extends AbstractUseCase. In fact AbstractUseCase init the transaction manager!", Objects.nonNull(tm)),
                    Precondition.init("no dpm provided", indexDpmArg != Precondition.INDEX_NOT_FOUND)
            );


            dpm = tm.createDataProviderManager((DataProviderManager) args[indexDpmArg]);
            // update dpm in args
            args[indexDpmArg] = dpm;

            tm.begin(dpm);

            //We choose to continue the call to the method in question
            returnObject = joinPoint.proceed(args);


            tm.commit(dpm);


        } catch (Throwable throwable) {

            if (Objects.nonNull(tm)) {
                tm.rollback(dpm);
            }

            throw throwable;

        } finally {

            LOG.debug("TransactionalAspect's aroundAdvice's body is now executed After yourMethodAround is called.");

            if (Objects.nonNull(tm)) {
                tm.close(dpm);
            }
        }
        return returnObject;
    }


    private TransactionManagerPT getInstanceOfTm(ProceedingJoinPoint joinPoint) throws TechnicalException {

        try {
            AbstractUsecase au = (AbstractUsecase) joinPoint.getThis();
            return au.getTransactionManager();
        } catch (ClassCastException cce) {
            return null;
        }
    }

    private <T> int getIndexOfClassArg(Object[] args, Class<T> clazz) {

        if (ArrayUtils.isNotEmpty(args)) {
            for (int i = 0; i < args.length; i++) {
                if (clazz.isInstance(args[i])) {
                    return i;
                }
            }
        }

        return -1;
    }
}
