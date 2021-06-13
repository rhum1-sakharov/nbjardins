package cglib;

import dynamic.proxy.annotation.RvlLogger;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HelloTest {

    Hello hello;

    @Before
    public void setUp() {

        hello = (Hello) Enhancer.create(Hello.class, new LoggerInterceptor());
    }

    @Test
    public void should_execute() {

        String name = "romain";


        Assertions.assertThat(hello.sayHello(name)).isEqualTo("hello " + name);

    }


}
