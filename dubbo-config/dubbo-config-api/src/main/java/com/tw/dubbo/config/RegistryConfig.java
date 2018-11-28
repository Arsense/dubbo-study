package com.tw.dubbo.config;

/**
 * @author tangwei
 * @date 2018/11/28 11:23
 */
public class RegistryConfig {

    //注册中心IP地址
    private String address;

    private Boolean isDefault;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean isDefault() {
        return isDefault;
    }


}
