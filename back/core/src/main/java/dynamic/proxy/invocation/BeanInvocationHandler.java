package dynamic.proxy.invocation;

import dynamic.proxy.interceptor.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BeanInvocationHandler implements InvocationHandler {

    private Object bean;
    private Interceptor[] interceptors;

    public BeanInvocationHandler(Object bean, Interceptor[] interceptors) {
        this.bean = bean;
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Invocation invocation = new Invocation(bean, interceptors, method, args);

        return invocation.nextInterceptor();
    }
}
