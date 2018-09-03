package com.remote.test.service;

import java.lang.reflect.Method;

/**
 * @author tangwei
 * @date 2018/8/31 10:08
 */
public class ProviderService {

    //服务接口
    private Class<?> serviceInterface;
    //服务实现
    private Object serviceObject;
    //服务端口
    private String port;
    //设置超时时间
    private int timeout;
    //设置线程数
    private int workThreads;

    private  Method serviceMethod;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public Method getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(Method serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
