package com.tw.dubbo.config;

/**
 * @author clay
 * @date 2018/11/28 0:08
 */
public class ProtocolConfig extends AbstractConfig {

    //协议名称
    private String name;
    //服务的IP地址
    private String host;

    private  String port;


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
