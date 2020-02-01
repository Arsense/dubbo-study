package com.tw.dubbo.common.config;

import com.tw.dubbo.common.rpc.ApplicationModel;

import java.util.List;

/**
 * @author clay
 * @date 2020/1/29 12:21
 */
public abstract class AbstractInterfaceConfig extends AbstractMethodConfig {


    /**
     * The application info
     */
    protected ApplicationConfig application;
    /**
     *
     */
    protected List<RegistryConfig> registries;

    /**
     * 是否发布接口
     */
    protected Boolean export;

    public ApplicationConfig getApplication() {
        if (application != null) {
            return application;
        }
        return null;
//        return ApplicationModel.getConfigManager().getApplicationOrElseThrow();
    }

    @Deprecated
    public void setApplication(ApplicationConfig application) {
        this.application = application;
        if (application != null) {
            ConfigManager configManager = ApplicationModel.getConfigManager();
            configManager.getApplication().orElseGet(() -> {
                configManager.setApplication(application);
                return application;
            });
        }
    }
    public void checkRegistry() {
        for (RegistryConfig registryConfig : registries) {
            if (!registryConfig.isValid()) {
                throw new IllegalStateException("No registry config found or it's not a valid config! " +
                        "The registry config is: " + registryConfig);
            }
        }

    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }
}
