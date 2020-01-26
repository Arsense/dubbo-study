package com.tw.dubbo.config;

import com.tw.dubbo.common.config.Parameter;
import com.tw.dubbo.common.logger.LoggerFactory;
import com.tw.dubbo.common.util.CollectionUtils;
import com.tw.dubbo.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tw.dubbo.common.config.Constants.*;
import static com.tw.dubbo.common.constants.QosConstants.ACCEPT_FOREIGN_IP;
import static com.tw.dubbo.common.constants.QosConstants.QOS_ENABLE;
import static com.tw.dubbo.common.constants.QosConstants.QOS_PORT;

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

    private MonitorConfig monitor;


    /**
     * The type of the log access
     */
    private String logger;

    /**
     * 保存线程转储的目录
     */
    private String dumpDirectory;

    /**
     * QOS启用开关
     * 用于评估服务方满足客户服务需求的能力。通过配置QoS，对企业的网络流量进行调控，避免并管理网络拥塞，减少报文的丢失率，同时也可以为企业用户提供专用带宽或者为不同的业务（语音、视频、数据等）提供差分服务。
     */
    private Boolean qosEnable;

    /**
     * QOS监听端口
     */
    private Integer qosPort;

    /**
     * 是否应该接受国外IP
     */
    private Boolean qosAcceptForeignIp;



    @Parameter(key = QOS_PORT)
    public Integer getQosPort() {
        return qosPort;
    }

    public void setQosPort(Integer qosPort) {
        this.qosPort = qosPort;
    }


    @Parameter(key = ACCEPT_FOREIGN_IP)
    public Boolean getQosAcceptForeignIp() {
        return qosAcceptForeignIp;
    }

    public void setQosAcceptForeignIp(Boolean qosAcceptForeignIp) {
        this.qosAcceptForeignIp = qosAcceptForeignIp;
    }

    @Parameter(key = QOS_ENABLE)
    public Boolean getQosEnable() {
        return qosEnable;
    }

    public void setQosEnable(Boolean qosEnable) {
        this.qosEnable = qosEnable;
    }

    @Parameter(key = "dump.directory")
    public String getDumpDirectory() {
        return dumpDirectory;
    }

    public void setDumpDirectory(String dumpDirectory) {
        this.dumpDirectory = dumpDirectory;
    }

    @SuppressWarnings({"unchecked"})
    public void setRegistries(List<? extends RegistryConfig> registries) {
        this.registries = (List<RegistryConfig>) registries;
    }

    public ApplicationConfig() {
    }

    public ApplicationConfig(String name) {
        setName(name);
    }
    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
        LoggerFactory.setLoggerAdapter(logger);
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



    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = new MonitorConfig(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
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


    public List<RegistryConfig> getRegistries() {
        return registries;
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
