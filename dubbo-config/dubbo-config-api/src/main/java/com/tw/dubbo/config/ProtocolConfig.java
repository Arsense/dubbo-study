package com.tw.dubbo.config;

import com.tw.dubbo.common.config.Parameter;

import java.util.Map;

/**
 * 不知道是否是RPC协议的公共类
 * @author clay
 * @date 2018/11/28 0:08
 */
public class ProtocolConfig extends AbstractConfig {

    /**
     * 协议名称
     */
    private String name;
    /**
     * 服务ip地址（当有多个网卡可用时）
     */
    private String host;
    /**
     * 协议端口号
     */
    private Integer port;

    /**
     *
      上下文路径
     */
    private String contextpath;

    /**
     * 线程池名称？
     */
    private String threadpool;

    /**
     * 核心线程池大小(固定大小)
     */
    private Integer corethreads;

    /**
     * 线程池大小(固定大小)
     */
    private Integer threads;

    /**
     * IO线程池大小(固定大小)
     */
    private Integer iothreads;

    /**
     *  线程池的队列长度
     */
    private Integer queues;

    /**
     * 最大可接受连接数
     */
    private Integer accepts;

    /**
     * The customized parameters
     */
    private Map<String, String> parameters;

    /**
     * 协议编解码器
     */
    private String codec;
    /**
     * 访问日志
     */
    private String accesslog;

    /**
     * 支持的telnet命令，用逗号分隔。
     */
    private String telnet;

    /**
     * 命令行提示
     */
    private String prompt;

    /**
     * 状态检测
     */
    private String status;
    /**
     * 是否注册
     */
    private Boolean register;

    /**
     * 扩展 什么扩展
     */
    private String extension;
    /**
     * whether it is a persistent connection
     */
    //TODO add this to provider config
    private Boolean keepAlive;
    /**
     * 优化器 优化什么？怎么优化 怎么用
     */
    // TODO add this to provider config
    private String optimizer;

    public String getOptimizer() {
        return optimizer;
    }

    public void setOptimizer(String optimizer) {
        this.optimizer = optimizer;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getRegister() {
        return register;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getTelnet() {
        return telnet;
    }

    public void setTelnet(String telnet) {
        this.telnet = telnet;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isRegister() {
        return register;
    }
    public void setRegister(Boolean register) {
        this.register = register;
    }

    public String getAccesslog() {
        return accesslog;
    }

    public void setAccesslog(String accesslog) {
        this.accesslog = accesslog;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public ProtocolConfig() {
    }

    public ProtocolConfig(String name) {
        setName(name);
    }

    public ProtocolConfig(String name, int port) {
        setName(name);
        setPort(port);
    }
    @Parameter(excluded = true)
    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    public String getThreadpool() {
        return threadpool;
    }

    public void setThreadpool(String threadpool) {
        this.threadpool = threadpool;
    }

    public Integer getCorethreads() {
        return corethreads;
    }

    public void setCorethreads(Integer corethreads) {
        this.corethreads = corethreads;
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

    @Parameter(excluded = true)
    public Integer getAccepts() {
        return accepts;
    }

    public void setAccepts(Integer accepts) {
        this.accepts = accepts;
    }

    @Deprecated
    @Parameter(excluded = true)
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Deprecated
    @Parameter(excluded = true)
    public String getPath() {
        return getContextpath();
    }

    @Deprecated
    public void setPath(String path) {
        setContextpath(path);
    }

    @Parameter(excluded = true)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Parameter(excluded = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //协议名称对应为ID
        this.updateIdIfAbsent(name);
    }






}
