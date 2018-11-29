package com.tw.dubbo.common.bytecode;

/**
 * 类的相关处理类
 *
 * @author tangwei
 * @date 2018/11/29 13:38
 */
public class ClassGenerator {

    public static boolean isDynamicClass(Class<?> cl) {
        return ClassGenerator.DC.class.isAssignableFrom(cl);
    }

    public static interface DC {
    } // dynamic class tag interface.
}
