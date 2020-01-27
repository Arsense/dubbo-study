package com.tw.dubbo.config;

import com.tw.dubbo.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提供者配置
 * @author clay
 * @date 2018/11/27 14:03
 */
public class ProviderConfig extends AbstractConfig {



    protected Boolean export;

    protected ApplicationConfig application;

    /**
     * 注册中心信息
     */
    protected List<RegistryConfig> registries;

    /**
     *服务将导出的协议列表
     *另请参见{@link#protocolIds}，其中只有一个可以工作。
     */
    protected List<ProtocolConfig> protocols;
    /**
     *服务将导出的协议的id列表
     *另请参阅{@link#protocols}，其中只有一个可以工作。
     */
    protected String protocolIds;

    /**
     * Service ip addresses (used when there are multiple network cards available)
     */
    private String host;

    /**
     * 服务提供者端口
     */
    private Integer port;

    /**
     *  Context path
     */
    private String contextpath;


    /**
     *是否使用默认协议
     */
    private Boolean isDefault;


    /**
     * Thread pool
     */
    private String threadpool;

    /**
     * Thread pool size (fixed size)
     */
    private Integer threads;

    /**
     * IO thread pool size (fixed size)
     */
    private Integer iothreads;

    /**
     * Thread pool queue length
     */
    private Integer queues;

    /**
     * Max acceptable connections
     */
    private Integer accepts;

    /**
     * Status check
     */
    private String status;

    /**
     * Wait time when stop
     */
    private Integer wait;
    /**
     * The server-side implementation model of the protocol
     */
    private String server;

    /**
     * The client-side implementation model of the protocol
     */
    private String client;

    /**
     * Supported telnet commands, separated with comma.
     */
    private String telnet;




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTelnet() {
        return telnet;
    }

    public void setTelnet(String telnet) {
        this.telnet = telnet;
    }

    public String getProtocolIds() {
        return protocolIds;
    }

    public void setProtocolIds(String protocolIds) {
        this.protocolIds = protocolIds;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getThreadpool() {
        return threadpool;
    }

    public void setThreadpool(String threadpool) {
        this.threadpool = threadpool;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getIothreads() {
        return iothreads;
    }

    public void setIothreads(Integer iothreads) {
        this.iothreads = iothreads;
    }

    public Integer getQueues() {
        return queues;
    }

    public void setQueues(Integer queues) {
        this.queues = queues;
    }

    public Integer getAccepts() {
        return accepts;
    }

    public void setAccepts(Integer accepts) {
        this.accepts = accepts;
    }

    /**
     * 设置协议信息
     * @param protocol
     */
    public void setProtocol(ProtocolConfig protocol) {
        setProtocols(new ArrayList<>(Arrays.asList(protocol)));
    }

    @Deprecated
    public void setProtocol(String protocol) {
        this.protocols = new ArrayList<>(Arrays.asList(new ProtocolConfig(protocol)));
    }


    @SuppressWarnings({"unchecked"})
    public void setProtocols(List<? extends ProtocolConfig> protocols) {
        this.protocols = (List<ProtocolConfig>) protocols;
    }


    public ProtocolConfig getProtocol() {
        return CollectionUtils.isEmpty(protocols) ? null : protocols.get(0);
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
