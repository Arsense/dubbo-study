package com.tw.dubbo.common.rpc;

import com.tw.dubbo.common.config.ApplicationConfig;
import com.tw.dubbo.common.config.ConfigManager;
import com.tw.dubbo.common.context.FrameworkExt;
import com.tw.dubbo.common.extension.ExtensionLoader;

import java.util.Optional;


/**
 *，{@code DubboBootstrap}和这个类目前被设计为
 *单例或静态（本身完全静态或使用一些静态字段）。所以这些例子
 *从它们返回的是进程范围的。如果您想在一个服务器中支持多个dubbo服务器
 *单进程，您可能需要重构这三个类。
 *
 *表示使用Dubbo的应用程序并存储基本元数据信息以供使用
 *在处理RPC调用期间。
 *<p>
 *ApplicationModel包含许多ProviderModel，这些ProviderModel是关于发布的服务的
 *以及许多关于订阅服务的消费者模型。
 *
 *@author clay
 * @date 2020/1/29 22:48
 *<p>
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
