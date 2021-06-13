package cglib;

import dynamic.proxy.annotation.RvlLogger;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LoggerInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object object, Method method, Object[] methodArgs, MethodProxy methodProxy) throws Throwable {

        if(method.isAnnotationPresent(RvlLogger.class)){
            System.out.println(String.format("%s.%s executed",method.getDeclaringClass().getSimpleName(),method.getName()));
        }

        return methodProxy.invokeSuper(object,methodArgs);
    }
}
