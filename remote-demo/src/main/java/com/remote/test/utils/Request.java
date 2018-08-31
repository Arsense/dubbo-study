package com.remote.test.utils;

import com.remote.test.service.ProviderService;

/**
 * @author tangwei
 * @date 2018/8/31 14:05
 */
public class Request {

    //UUID,唯一标识一次返回值
    private String uniqueKey;
    //服务提供者信息
    private ProviderService providerService;

    private int timeout;

    private String invokeMethodName;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public ProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }



    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public void setInvokeMethodName(String invokeMethodName) {
        this.invokeMethodName = invokeMethodName;
    }

    @Override
    public String toString() {
        return "Request{" +
                "uniqueKey='" + uniqueKey + '\'' +
                ", providerService=" + providerService +
                ", timeout=" + timeout +
                ", invokeMethodName='" + invokeMethodName + '\'' +
                '}';
    }
}
