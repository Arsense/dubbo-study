package com.tw.dubbo.common.extension.factory;

import com.tw.dubbo.common.extension.ExtensionFactory;

/**
 * @author tangwei
 * @date 2020/2/9 22:22
 */
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        return null;
    }
}
