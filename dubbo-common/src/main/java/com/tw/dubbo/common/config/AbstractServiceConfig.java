package com.tw.dubbo.common.config;

import java.util.List;

/**
 * @author tangwei
 * @date 2020/2/1 16:55
 */
public abstract class AbstractServiceConfig  extends AbstractInterfaceConfig {

    protected String token;

    /**
     * The service version
     */
    protected String version;

    /**
     * The service group
     */
    protected String group;

    /**
     * registry centers
     */
    protected List<RegistryConfig> registries;

    protected List<ProtocolConfig> protocols;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

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
