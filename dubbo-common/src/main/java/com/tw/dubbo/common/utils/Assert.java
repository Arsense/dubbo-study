package com.tw.dubbo.common.utils;

/**
 * 工具校验类
 * @author tangwei
 * @date 2020/2/1 17:25
 */
public class Assert {

    protected Assert() {
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }


    public static void notEmptyString(String str,String message) {
        if(StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void notNull(Object obj, RuntimeException exception) {
        if (obj == null) {
            throw exception;
        }
    }

}
