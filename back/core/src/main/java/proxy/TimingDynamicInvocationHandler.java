package proxy;

import annotations.IRvlLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimingDynamicInvocationHandler<T> implements InvocationHandler {

    private static Logger LOG = LoggerFactory.getLogger(TimingDynamicInvocationHandler.class);

    private final T instance;


    public TimingDynamicInvocationHandler(T instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        long start = System.nanoTime();
//        Object result = methods.get(method.getName()).invoke(original, args);
//        long elapsed = System.nanoTime() - start;

//        LOG.info("Executing {} finished in {} ns", method.getName(),
//                elapsed);
//
//        return result;

        LOG.info("hello world");

        return method.invoke(instance,args);

    }
}
