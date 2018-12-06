package com.tw.dubbo.common;

/**
 * @author tangwei
 * @date 2018/12/5 16:25
 */
public class ReflectUtils {

    public static String getName(Class<?> c) {
        //fixme 没有处理C是数组的情况
        return c.getName();

    }
}
