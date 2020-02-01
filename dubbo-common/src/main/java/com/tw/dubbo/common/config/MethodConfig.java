package com.tw.dubbo.common.config;

import com.tw.dubbo.common.config.annotation.Parameter;

/**
 * @author clay
 * @date 2020/1/29 12:10
 */
public class MethodConfig extends AbstractMethodConfig {

    /**
     * The method name
     */
    private String name;

    @Parameter(excluded = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
