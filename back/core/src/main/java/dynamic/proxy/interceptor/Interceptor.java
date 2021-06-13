package dynamic.proxy.interceptor;

import dynamic.proxy.invocation.Invocation;

public interface Interceptor {

    /**
     * @param invocation
     * @return
     */
    Object invoke(Invocation invocation);
}
