package dynamic.proxy.interceptor;

import dynamic.proxy.invocation.Invocation;
import dynamic.proxy.annotation.RvlLogger;

import java.lang.reflect.Method;

public class LoggingInterceptor implements Interceptor {


    @Override
    public Object invoke(Invocation invocation) {

        Method method = invocation.getMethod();

        if (method.isAnnotationPresent(RvlLogger.class)) {
            System.out.println(String.format("Invocation of %s.%s", method.getClass().getSimpleName(), method.getName()));
        }

        return invocation.nextInterceptor();
    }
}
