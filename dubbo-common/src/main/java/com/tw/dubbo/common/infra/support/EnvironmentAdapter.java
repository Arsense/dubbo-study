package com.tw.dubbo.common.infra.support;

import com.tw.dubbo.common.infra.InfraAdapter;

import java.util.Map;

/**
 * @author clay
 * @date 2020/1/26 15:08
 */
public class EnvironmentAdapter implements InfraAdapter {
    @Override
    public Map<String, String> getExtraAttributes(Map<String, String> params) {
        return null;
    }

    @Override
    public String getAttribute(String key) {
        return null;
    }
}
