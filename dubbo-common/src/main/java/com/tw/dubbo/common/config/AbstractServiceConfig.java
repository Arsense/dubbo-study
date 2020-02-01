package com.tw.dubbo.common.config;

import java.util.List;

/**
 * @author tangwei
 * @date 2020/2/1 16:55
 */
public abstract class AbstractServiceConfig  extends AbstractInterfaceConfig {

    /**
     * registry centers
     */
    protected List<RegistryConfig> registries;

    protected List<ProtocolConfig> protocols;

    public List<ProtocolConfig> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    @Override
    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    @Override
    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }
}
