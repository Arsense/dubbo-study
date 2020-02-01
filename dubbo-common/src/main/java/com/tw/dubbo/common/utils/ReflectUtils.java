package com.tw.dubbo.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

/**
 *
 * 反射工具类
 * @author tangwei
 * @date 2020/1/31 19:02
 */
public class ReflectUtils {

    /**
     * propertyName为default的时候 其实是通过一个内省的方式获取所有的get方法 然后过滤找到
     * getDefault方法 然后调用获取值
     * @param bean
     * @param propertyName
     * @param <T>
     * @return
     */
    public static <T> T getProperty(Object bean, String propertyName) {
        Class<?> beanClass = bean.getClass();
        BeanInfo beanInfo = null;
        T propertyValue = null;

        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
            propertyValue = (T) Stream.of(beanInfo.getPropertyDescriptors())
                            .filter(propertyDescriptor -> propertyName.equals(propertyDescriptor.getName()))
                    .map(PropertyDescriptor::getReadMethod)
                    .findFirst()
                    .map(method -> {
                        try {
                            return method.invoke(bean);
                        } catch (Exception e) {
                        }
                        return null;
                    }).get();

        } catch (IntrospectionException e) {
            //异常不做处理 返回空即可
        }


        return propertyValue;
    }
}
