package com.tw.dubbo.common.utils;

/**
 * @author clay
 * @date 2018/11/29 14:33
 */
public class NetUtils {

    public static boolean isInvalidLocalHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0");
    }
}
