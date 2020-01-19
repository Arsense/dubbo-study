package com.tw.dubbo.config;

import java.util.List;

/**
 * @author clay
 * @date 2018/11/27 14:03
 */
public class ProviderConfig  extends AbstractConfig{



    protected Boolean export;

    protected ApplicationConfig application;

    // registry centers
    protected List<RegistryConfig> registries;

    protected List<ProtocolConfig> protocols;

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }

    public List<ProtocolConfig> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }


}
