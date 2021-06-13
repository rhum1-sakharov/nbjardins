package utils.threads;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import utils.ThreadUtil;

@RunWith(MockitoJUnitRunner.class)
public class ThreadTest {

    @Mock
    static ThreadUtil threadUtil;

    @Test
    public void showNbThreads(){

        System.out.println(ThreadUtil.showNbThreads());

        int maxPool = ThreadUtil.maxDbPoolConnections(0.6f * 0.6f);

       System.out.println("max pool size : "+maxPool);
    }

}
