package dynamic.proxy.container;

import dynamic.proxy.interceptor.BeanInterceptor;
import dynamic.proxy.interceptor.Interceptor;
import dynamic.proxy.interceptor.LoggingInterceptor;
import dynamic.proxy.invocation.BeanInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MiniContainer {

    private Map<Class<?>, Class<?>> registry;

    private Interceptor[] interceptors;

    public MiniContainer() {

        registry = new HashMap<>();

        this.interceptors = new Interceptor[]{
                new LoggingInterceptor(),
                new BeanInterceptor()
        };
    }

    public <T> MiniContainer register(Class<? extends T> impl, Class<T> iface) {
        registry.put(iface, impl);
        return this;
    }


    public <T> T createBean(Class<T> iface) {
        try {

            Class<? extends T> impl = (Class<? extends T>) registry.get(iface);

            BeanInvocationHandler handler = new BeanInvocationHandler(impl.newInstance(), interceptors);
            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iface}, handler);

        } catch (Exception e) {
            throw new ContainerException(e.getMessage());
        }
    }
}
