package utils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class ThreadUtil {

    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private static final CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();


    public static int nbPhysicalCpuCores() {
        return centralProcessor.getPhysicalProcessorCount();
    }

    public static int nbLogicalCpuCores() {
        return centralProcessor.getLogicalProcessorCount();
    }

    public static String showNbThreads() {

        StringBuilder sb = new StringBuilder();

        sb.append("nb physical cpu core : " + nbPhysicalCpuCores());
        sb.append(System.lineSeparator());
        sb.append("nb logical cpu core : " + nbLogicalCpuCores());

        return sb.toString();

    }

    /**
     * connections = ((core_count * 2)) + effective_spindle_count) * poolRatio
     *
     * @return
     */
    public static int adjustDbPool(float poolRatio) {

        int core_count = nbPhysicalCpuCores();

        return (int) (((core_count * 2) + 1) * poolRatio);

    }
}
