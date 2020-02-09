package com.tw.dubbo.common.extension.factory;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionFactory;

/**
 * @author tangwei
 * @date 2020/2/9 22:13
 */
@Adaptive
public class AdaptiveExtensionFactory  implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        return null;
    }
}
