package com.tw.dubbo.common.extension.factory;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionFactory;
import com.tw.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author clay
 * @date 2020/2/9 22:13
 */
@Adaptive
public class AdaptiveExtensionFactory  implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    /**
     * getAdaptiveExtensionClass().newInstance()方法触发
     */
    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();

        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }

        factories = Collections.unmodifiableList(list);

    }


    @Override
    public <T> T getExtension(Class<T> type, String name) {
        return null;
    }
}
