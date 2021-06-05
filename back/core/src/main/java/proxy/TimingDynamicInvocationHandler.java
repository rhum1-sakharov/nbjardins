package proxy;

import annotations.IRvlLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimingDynamicInvocationHandler implements InvocationHandler {

    private static Logger LOG = LoggerFactory.getLogger(TimingDynamicInvocationHandler.class);

    private final Map<String, Method> methods = new HashMap<>();

    private final IRvlLogger original;

    public TimingDynamicInvocationHandler(IRvlLogger rvlLogger) {
        this.original = rvlLogger;

        for(Method method: original.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start = System.nanoTime();
        Object result = methods.get(method.getName()).invoke(original, args);
        long elapsed = System.nanoTime() - start;

        LOG.info("Executing {} finished in {} ns", method.getName(),
                elapsed);

        return result;

    }
}
