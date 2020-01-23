package com.tw.dubbo.config;

/**
 * @author clay
 * @date 2018/11/28 11:23
 */
public class RegistryConfig  extends AbstractConfig{

    /**
     * 注册中心IP地址
     */
    private String address;
    /**
     * 协议与注册中心
     */
    private String protocol;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


}
