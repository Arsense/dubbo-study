package com.remote.test.service;

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
