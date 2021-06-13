package annotations;

import dynamic.proxy.annotation.RvlLogger;
import dynamic.proxy.bean.ExecuteBean;
import dynamic.proxy.bean.IBean;
import dynamic.proxy.container.MiniContainer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import proxy.TimingDynamicInvocationHandler;

import java.lang.reflect.Proxy;

@RunWith(MockitoJUnitRunner.class)
public class ExecuteBeanTest {

    MiniContainer mc;

    IBean usecase;

    @Before
    public void setUp() {
        mc = new MiniContainer();
        mc.register(ExecuteBean.class, IBean.class);

        usecase = mc.createBean(IBean.class);


    }

    @Test
    public void should_execute() {

        String result = usecase.executeMe();

        Assertions.assertThat(result).isEqualTo("executeMe");

    }


}
