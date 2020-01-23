package com.tw.dubbo.config;

import com.tw.dubbo.common.config.Parameter;
import com.tw.dubbo.common.util.CollectionUtils;
import com.tw.dubbo.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tw.dubbo.common.config.Constants.*;

/**
 *  应用程序基础config类
 * @author clay
 * @date 2018/11/28 10:52
 */
public class ApplicationConfig extends AbstractConfig {

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用程序版本号
     */
    private String version;
    /**
     * 程序拥有者 什么用途 权限分配？
     */
    private String owner;
    /**
     * 申请机构（BU）
     */
    private String organization;
    /**
     * 架构层
     */
    private String architecture;

    /**
     * Environment, e.g. dev, test or production
     */
    private String environment;
    /**
     * Registry centers
     */
    private List<RegistryConfig> registries;

    @SuppressWarnings({"unchecked"})
    public void setRegistries(List<? extends RegistryConfig> registries) {
        this.registries = (List<RegistryConfig>) registries;
    }

    public ApplicationConfig() {
    }

    public ApplicationConfig(String name) {
        setName(name);
    }

    @Parameter(key = "application", required = true, useKeyAsProperty = false)
    public String getName() {
        return name;
    }

    public void setRegistry(RegistryConfig registry) {
        List<RegistryConfig> registries = new ArrayList<RegistryConfig>(1);
        registries.add(registry);
        this.registries = registries;
    }

    public void setName(String name) {
        this.name = name;
        if (StringUtils.isEmpty(id)) {
            id = name;
        }
    }
    public RegistryConfig getRegistry() {
        return CollectionUtils.isEmpty(registries) ? null : registries.get(0);
    }
    public String getEnvironment() {
        return environment;
    }

    /**
     * 环境只有 dev 测试 product生成环境 develop开发环境
     * @param environment
     */
    public void setEnvironment(String environment) {
        if (environment != null) {
            if (!(DEVELOPMENT_ENVIRONMENT.equals(environment)
                    || TEST_ENVIRONMENT.equals(environment)
                    || PRODUCTION_ENVIRONMENT.equals(environment))) {

                throw new IllegalStateException(String.format("Unsupported environment: %s, only support %s/%s/%s, default is %s.",
                        environment,
                        DEVELOPMENT_ENVIRONMENT,
                        TEST_ENVIRONMENT,
                        PRODUCTION_ENVIRONMENT,
                        PRODUCTION_ENVIRONMENT));
            }
        }
        this.environment = environment;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Parameter(key = "application.version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
