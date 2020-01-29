package com.tw.dubbo.common.rpc;

import com.tw.dubbo.common.config.ApplicationConfig;
import com.tw.dubbo.common.config.ConfigManager;
import com.tw.dubbo.common.context.FrameworkExt;
import com.tw.dubbo.common.extension.ExtensionLoader;

import java.util.Optional;

/**
 * @author clay
 * @date 2020/1/29 22:48
 */
public class ApplicationModel {
    private static final ExtensionLoader<FrameworkExt> loader = ExtensionLoader.getExtensionLoader(FrameworkExt.class);

    public static ConfigManager getConfigManager() {
        return (ConfigManager) loader.getExtension(ConfigManager.NAME);
    }


    public Optional<ApplicationConfig> getApplication() {
        return null;
    }


    public ApplicationConfig getApplicationOrElseThrow() {
        return getApplication().orElseThrow(()-> new IllegalStateException("There's no ApplicationConfig specified."));

    }
}
