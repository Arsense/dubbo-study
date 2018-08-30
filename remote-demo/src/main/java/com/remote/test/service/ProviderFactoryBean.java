package com.remote.test.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/** FactoryBean 工厂Bean定义
 * @author tangwei
 * @date 2018/8/30 17:38
 */
public class ProviderFactoryBean implements FactoryBean, InitializingBean {
//    InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法

    //服务接口
    private Class<?> serviceInterface;

    //服务实现
    private Object serviceObject;
    //服务端口
    private String port;



    public void afterPropertiesSet() throws Exception {
            //这里启动Netty
    }


    public Object getObject() throws Exception {
        return serviceObject;
    }


    public boolean isSingleton() {
        return true;
    }

    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }



    @Override
    public String toString() {
        return "ProviderFactoryBean{" +
                "serviceInterface=" + serviceInterface +
                ", port='" + port + '\'' +
                '}';
    }



}
