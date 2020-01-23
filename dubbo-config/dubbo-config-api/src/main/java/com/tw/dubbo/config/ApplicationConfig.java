package com.tw.dubbo.config;

import com.tw.dubbo.common.util.StringUtils;

/**
 *  应用信息类
 * @author clay
 * @date 2018/11/28 10:52
 */
public class ApplicationConfig extends AbstractConfig {

    /**
     * 应用名称
     */
    private String name;
    public ApplicationConfig(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
        if (StringUtils.isEmpty(id)) {
            id = name;
        }
    }
}
