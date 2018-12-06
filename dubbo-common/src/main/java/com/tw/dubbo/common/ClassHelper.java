package com.tw.dubbo.common;

/**
 * @author tangwei
 * @date 2018/12/5 16:16
 */
public class ClassHelper {

    public static ClassLoader getClassLoader(Class<?> rowClass) {
        //根据接口创建类加载器 通过反射来增加一些属性设置的方法
        //私有属性
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = rowClass.getClassLoader();
        }
        return cl;


    }



}
