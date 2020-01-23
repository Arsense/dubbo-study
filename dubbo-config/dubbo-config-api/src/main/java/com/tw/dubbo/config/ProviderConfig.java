package com.tw.dubbo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author clay
 * @date 2018/11/27 14:03
 */
public class ProviderConfig  extends AbstractConfig{



    protected Boolean export;

    protected ApplicationConfig application;

    /**
     * 注册中心信息
     */
    protected List<RegistryConfig> registries;

    protected List<ProtocolConfig> protocols;

    private String host;

    /**
     * 设置协议信息
     * @param protocol
     */
    public void setProtocol(ProtocolConfig protocol) {
        setProtocols(new ArrayList<>(Arrays.asList(protocol)));
    }

    @SuppressWarnings({"unchecked"})
    public void setProtocols(List<? extends ProtocolConfig> protocols) {
        this.protocols = (List<ProtocolConfig>) protocols;
    }

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



    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }


}
