package dynamic.proxy.invocation;

import dynamic.proxy.interceptor.Interceptor;
import lombok.Getter;

import java.lang.reflect.Method;

public class Invocation {

    @Getter
    private Object bean;

    private Interceptor[] interceptors;

    @Getter
    private Method method;

    @Getter
    private Object[] args;

    private int index;

    public Invocation(Object bean, Interceptor[] interceptors, Method method, Object[] args) {
        this.bean = bean;
        this.interceptors = interceptors;
        this.method = method;
        this.args = args;
    }

    public Object nextInterceptor() {
        try {
            return interceptors[index++].invoke(this);
        } finally {
            index--;
        }
    }

}
