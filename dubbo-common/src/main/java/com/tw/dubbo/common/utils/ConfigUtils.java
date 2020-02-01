package com.tw.dubbo.common.utils;

/**
 * @author tangwei
 * @date 2020/2/1 17:35
 */
public class ConfigUtils {

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }


    public static boolean isEmpty(String value) {
        return StringUtils.isEmpty(value)
                || "false".equalsIgnoreCase(value)
                || "0".equalsIgnoreCase(value)
                || "null".equalsIgnoreCase(value)
                || "N/A".equalsIgnoreCase(value);
    }

}
