package annotations;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import proxy.TimingDynamicInvocationHandler;

import java.lang.reflect.Proxy;

@RunWith(MockitoJUnitRunner.class)
public class RvlLoggerTest {

    IRvlLogger usecase;

    @Before
    public void setUp() {

        usecase = (IRvlLogger) Proxy.newProxyInstance(RvlLoggerTest.class.getClassLoader(), new Class[]{IRvlLogger.class}, new TimingDynamicInvocationHandler(new RvlLoggerImpl()));


    }

    @Test
    public void should_execute() {

        String result = usecase.executeMe();

        Assertions.assertThat(result).isEqualTo("executeMe");

    }


}
