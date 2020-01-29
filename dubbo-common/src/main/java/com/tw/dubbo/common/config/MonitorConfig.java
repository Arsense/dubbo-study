package com.tw.dubbo.common.config;

import com.tw.dubbo.common.config.annotation.Parameter;

/**
 * @author clay
 * @date 2018/11/28 18:13
 */
public class MonitorConfig extends AbstractConfig {

    /**
     * The monitor address
     */
    private String address;


    public MonitorConfig() {
    }

    public MonitorConfig(String address) {
        this.address = address;
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
