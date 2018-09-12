package com.remote.test.provider;

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
    //用户凭证
    private  String appKey;
    private String serverIp;

    public ProviderService copy(){
        ProviderService providerService = new ProviderService();
        providerService.setPort(port);
        providerService.setServerIp(serverIp);
        providerService.setServiceInterface(serviceInterface);
        providerService.setServiceMethod(serviceMethod);
        providerService.setServiceObject(serviceObject);
        providerService.setAppKey(appKey);
        providerService.setServerIp(serverIp);
        providerService.setTimeout(timeout);
        providerService.setWorkThreads(workThreads);
        return providerService;
    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "ProviderService{" +
                "serviceInterface=" + serviceInterface +
                ", serviceObject=" + serviceObject +
                ", port='" + port + '\'' +
                ", timeout=" + timeout +
                ", workThreads=" + workThreads +
                ", serviceMethod=" + serviceMethod +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
