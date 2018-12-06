package com.tw.dubbo.common.bytecode;

import com.tw.dubbo.common.ClassHelper;
import com.tw.dubbo.common.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

        Wrapper ret = WRAPPER_MAP.computeIfAbsent(c, k -> makeWrapper(c));
        return ret;
    }

    //整个包装不是很明白
    private static Wrapper makeWrapper(Class<?> rowClass) throws IllegalAccessException, InstantiationException {
        //私有属性不出来
        if (rowClass.isPrimitive())
            throw new IllegalArgumentException("Can not create wrapper for primitive type: " + rowClass);
        ClassLoader classLoader = ClassHelper.getClassLoader(rowClass);

        StringBuilder newMethod1 = new StringBuilder("public void setPropertyValue(Object o, String n, Object v){ ");
        StringBuilder newMethod2 = new StringBuilder("public Object getPropertyValue(Object o, String n){ ");
        StringBuilder newMethod3 = new StringBuilder("public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws " + InvocationTargetException.class.getName() + "{ ");

        Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();

        Map<String, Method> methodMap = new LinkedHashMap<String, Method>(); // <method desc, Method instance>
        for (Field field : rowClass.getFields()) {
            String fieldName = field.getName();
            Class<?> fieldType =field.getType();
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers()))
                continue;

            newMethod1.append(" if( $2.equals(\"").append(fieldName).append("\") ){ w.").append(fieldName).append("=").append(arg(fieldType, "$3")).append("; return; }");
            newMethod1.append(" if( $2.equals(\"").append(fieldName).append("\") ){ return ($w)w.").append(fieldName).append("; }");
            propertyMap.put(fieldName, fieldType);
        }

        ClassGenerator instance = ClassGenerator.newInstance(classLoader);

        try{

            Class<?> wc = instance.toClass();
            return (Wrapper) wc.newInstance();

        }catch (Exception e){

        }finally {
//            instance.clear();
        }





    }


    private static String arg(Class<?> cl, String name) {
        if (cl.isPrimitive()) {
            if (cl == Boolean.TYPE)
                return "((Boolean)" + name + ").booleanValue()";
            if (cl == Byte.TYPE)
                return "((Byte)" + name + ").byteValue()";
            if (cl == Character.TYPE)
                return "((Character)" + name + ").charValue()";
            if (cl == Double.TYPE)
                return "((Number)" + name + ").doubleValue()";
            if (cl == Float.TYPE)
                return "((Number)" + name + ").floatValue()";
            if (cl == Integer.TYPE)
                return "((Number)" + name + ").intValue()";
            if (cl == Long.TYPE)
                return "((Number)" + name + ").longValue()";
            if (cl == Short.TYPE)
                return "((Number)" + name + ").shortValue()";
            throw new RuntimeException("Unknown primitive type: " + cl.getName());
        }
        return "(" + ReflectUtils.getName(cl) + ")" + name;
    }

    /**
     * get method name array.
     *
     * @return method name array.
     */
    abstract public String[] getMethodNames();
}
