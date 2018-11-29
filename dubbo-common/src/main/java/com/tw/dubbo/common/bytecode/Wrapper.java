package com.tw.dubbo.common.bytecode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangwei
 * @date 2018/11/29 11:35
 */
public abstract class Wrapper {

    private static final Map<Class<?>, Wrapper> WRAPPER_MAP = new ConcurrentHashMap<Class<?>, Wrapper>();

    /**
     * 不是特别理解
     * @param c
     * @return
     */
    public static Wrapper getWrapper(Class<?> c) {
        //动态类需要处理的
//        while (ClassGenerator.isDynamicClass(c)) // can not wrapper on dynamic class.
//            c = c.getSuperclass();

        Wrapper ret = WRAPPER_MAP.get(c);
        if (ret == null) {
            ret = makeWrapper(c);
            WRAPPER_MAP.put(c, ret);
        }
        return ret;
    }

    //整个包装不是很明白
    private static Wrapper makeWrapper(Class<?> c) {
        return new Wrapper() {
            public String[] getMethodNames() {
                return new String[0];
            }
        };
    }

    /**
     * get method name array.
     *
     * @return method name array.
     */
    abstract public String[] getMethodNames();
}
