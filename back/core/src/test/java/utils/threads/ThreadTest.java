package utils.threads;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import utils.ThreadUtil;

@RunWith(MockitoJUnitRunner.class)
public class ThreadTest {

    @Mock
    static ThreadUtil threadUtil;

    @Test
    public void showNbThreads(){

        System.out.println(ThreadUtil.showNbThreads());

        int maxPool = ThreadUtil.adjustDbPool(0.4f);

       System.out.println("max pool size : "+maxPool);
    }

}
