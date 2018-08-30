package com.remote.test.service;

/**
 * @author tangwei
 * @date 2018/8/30 17:38
 */
public class ProviderFactoryBean {

    //服务接口
    private Class<?> serviceInterface;

    private String port;


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
