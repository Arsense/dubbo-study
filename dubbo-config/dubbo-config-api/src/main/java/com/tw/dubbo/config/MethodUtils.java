package com.tw.dubbo.config;

import java.lang.reflect.Method; /**
 * @author tangwei
 * @date 2020/1/19 20:31
 */
public class MethodUtils {
    public static boolean isGetter(Method method) {
        return true;
    }
}
