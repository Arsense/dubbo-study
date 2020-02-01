package com.tw.dubbo.common.config;

import com.tw.dubbo.common.rpc.ApplicationModel;
import com.tw.dubbo.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2020/1/30 17:47
 */
public abstract class ServiceConfigBase<T> extends AbstractServiceConfig {


    /**
     * 接口实现
     */
    protected T ref;
    /**
     * 接口类型
     */
    protected Class<?> interfaceClass;
    /**
     * 接口名
     */
    protected String interfaceName;

    /**
     * 提供者
     */
    protected ProviderConfig provider;

    protected boolean shouldExport() {
        //优先级是provider的export 就是可以提供者执行设置
        Boolean export = getExport();
        // default value is true
        return export == null ? true : export;
    }

    public List<ProviderConfig> getProviders() {
        return convertProtocolToProvider(protocols);
    }
    /**
     *  Protocol 转换成Provider
     * @param protocols
     * @return
     */
    private static List<ProviderConfig> convertProtocolToProvider(List<ProtocolConfig> protocols) {
        if (protocols == null || protocols.isEmpty()) {
            return null;
        }
        //细节 省空间protocols.size()
        List<ProviderConfig> providers = new ArrayList<ProviderConfig>(protocols.size());
        for (ProtocolConfig provider : protocols) {
            providers.add(convertProtocolToProvider(provider));
        }
        return providers;
    }


    @Deprecated
    private static ProviderConfig convertProtocolToProvider(ProtocolConfig protocol) {
        ProviderConfig provider = new ProviderConfig();
        provider.setProtocol(protocol);
        provider.setHost(protocol.getHost());
        provider.setPort(protocol.getPort());
        provider.setThreads(protocol.getThreads());
        return provider;
    }


    @Deprecated
    private static ProtocolConfig convertProviderToProtocol(ProviderConfig provider) {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(provider.getProtocol().getName());
        protocol.setHost(provider.getHost());
        protocol.setPort(provider.getPort());
        protocol.setThreads(provider.getThreads());
        return protocol;
    }

    /**
     * @deprecated Replace to setProtocols()
     */
    // @Deprecated不允许改方法名

    public void setProviders(List<ProviderConfig> providers) {
        this.protocols = convertProviderToProtocol(providers);
    }


    @Deprecated
    private static List<ProtocolConfig> convertProviderToProtocol(List<ProviderConfig> providers) {
        if (CollectionUtils.isEmpty(providers)) {
            return null;
        }
        List<ProtocolConfig> protocols = new ArrayList<ProtocolConfig>(providers.size());
        for (ProviderConfig provider : providers) {
            protocols.add(convertProviderToProtocol(provider));
        }
        return protocols;
    }


    @Override
    public Boolean getExport() {
        return (export == null && provider != null) ? provider.getExport() : export;
    }


    /**
     * 是否需要创建默认的provider
     */
    public void checkDefaultProvider() {
        createProviderIfAbsent();
    }

    /**
     * 如果provider为空则创建一个
     */
    private void createProviderIfAbsent() {
        if (provider != null) {
            return;
        }

        setProvider(
                ApplicationModel.getConfigManager()
                .getDefaultProvider()
                .orElseGet(() -> {
                            ProviderConfig providerConfig = new ProviderConfig();
                            providerConfig.refresh();
                            return providerConfig;
               })
        );

    }


    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        ApplicationModel.getConfigManager().addProvider(provider);
        this.provider = provider;
    }
}
