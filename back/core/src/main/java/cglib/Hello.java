package cglib;

import dynamic.proxy.annotation.RvlLogger;

public class Hello {

    @RvlLogger
    public String sayHello(String name) {
        return "hello " + name;
    }
}
