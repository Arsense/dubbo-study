package com.tw.dubbo.config;

import com.tw.dubbo.common.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author tangwei
 * @date 2020/1/19 20:31
 */
public class MethodUtils {
    /**
     * 根据字符串前面的属性与获取方法是否公私有判断是否是getter方法
     * @param method
     * @return
     */
    public static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith("get") || name.startsWith("is"))
                && !"get".equals(name) && !"is".equals(name)
                && !"getClass".equals(name) && !"getObject".equals(name)
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0
                && ClassUtils.isPrimitive(method.getReturnType());
    }
}
