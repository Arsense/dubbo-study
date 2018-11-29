package com.tw.dubbo.config;

/**
 * @author tangwei
 * @date 2018/11/28 0:08
 */
public class ProtocolConfig {

    //服务的IP地址
    private String host;
    private Boolean isDefault;

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
