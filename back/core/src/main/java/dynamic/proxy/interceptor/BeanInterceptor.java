package dynamic.proxy.interceptor;

import dynamic.proxy.invocation.Invocation;
import dynamic.proxy.invocation.InvocationException;

import java.lang.reflect.Method;

public class BeanInterceptor implements Interceptor {


    @Override
    public Object invoke(Invocation invocation) {

        try {
            Object bean = invocation.getBean();
            Method method = invocation.getMethod();
            Object[] args = invocation.getArgs();
            return method.invoke(bean, args);
        } catch (Exception e) {
            throw new InvocationException(e.getMessage());
        }
    }
}
