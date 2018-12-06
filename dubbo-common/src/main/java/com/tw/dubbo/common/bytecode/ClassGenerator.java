package com.tw.dubbo.common.bytecode;

import javassist.ClassPool;
import javassist.LoaderClassPath;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类的相关处理类
 *
 * @author tangwei
 * @date 2018/11/29 13:38
 */
public class ClassGenerator {
    private ClassPool classPool;


    public ClassGenerator(ClassPool classPool) {
        this.classPool = classPool;
    }

    public static boolean isDynamicClass(Class<?> cl) {
        return ClassGenerator.DC.class.isAssignableFrom(cl);
    }

    private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<ClassLoader, ClassPool>();

    public static interface DC {
    } // dynamic class tag interface.


    public static ClassGenerator newInstance(ClassLoader classLoader) {
        return new ClassGenerator(getClassPool(Thread.currentThread().getContextClassLoader()));
    }

    public Class<?> toClass() {
        return null;
    }
    public static ClassPool getClassPool(ClassLoader loader) {
        if (loader == null)
            return ClassPool.getDefault();

        ClassPool pool = POOL_MAP.get(loader);
        if (pool == null) {
            pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(loader));
            POOL_MAP.put(loader, pool);
        }
        return pool;
    }

}
