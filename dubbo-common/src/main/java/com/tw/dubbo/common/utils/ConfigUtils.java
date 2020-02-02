package com.tw.dubbo.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author tangwei
 * @date 2020/2/1 17:35
 */
public class ConfigUtils {

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    private static int PID = -1;

    public static boolean isEmpty(String value) {
        return StringUtils.isEmpty(value)
                || "false".equalsIgnoreCase(value)
                || "0".equalsIgnoreCase(value)
                || "null".equalsIgnoreCase(value)
                || "N/A".equalsIgnoreCase(value);
    }


    public static int getPid() {
        if (PID < 0) {
            try {
                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
                String name = runtime.getName(); // format: "pid@hostname"
                PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Throwable e) {
                PID = 0;
            }
        }
        return PID;
    }

}
