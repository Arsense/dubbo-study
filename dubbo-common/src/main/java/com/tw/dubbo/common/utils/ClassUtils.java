package com.tw.dubbo.common.utils;

/**
 * @author clay
 * @date 2020/1/20 15:08
 */
public class ClassUtils {

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || type == Character.class
                || type == Boolean.class
                || type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class
                || type == Object.class;
    }

}
