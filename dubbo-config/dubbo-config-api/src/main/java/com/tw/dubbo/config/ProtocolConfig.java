package com.tw.dubbo.config;

/**
 * @author tangwei
 * @date 2018/11/28 0:08
 */
public class ProtocolConfig {

    //协议名称
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
