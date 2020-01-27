package com.tw.dubbo.config;


import java.util.*;
/**
 * @author clay
 * @date 2018/11/28 11:23
 */
public class RegistryConfig  extends AbstractConfig{

    /**
     * 注册中心IP地址
     */
    private String address;
    /**
     * 协议与注册中心
     */
    private String protocol;

    /**
     * 登录注册中心的用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 停止等待时间
     */
    private Integer wait;


    /**
     * 启动时是否检查注册中心是否可用
     * @return
     */
    private Boolean check;

    /**
     * 用于保存注册中心动态列表的文件
     */
    private String file;

    private String server;

    private String client;

    /**
     *  注册中心的请求超时（毫秒）
     */
    private Integer timeout;
    /**
     * 注册中心会话超时时间
     */
    private Integer session;

    /**
     * 是否允许动态服务在注册中心注册
     */
    private Boolean dynamic;

    /**
     * 是都在注册中心暴露
   */
    private Boolean register;

    /**
     *影响通信量在注册中心之间的分布方式，在订阅多个注册中心时很有用，可用选项：
     *一。区域感知，特定类型的流量总是根据流量的来源流向一个注册表。
     */
    private String cluster;

    /**
     * 服务注册表所在的组
     */
    private String group;
    /**
     * 版本号
      */
    private String version;
    /**
     *
     自定义参数 扩展项
     */
    private Map<String, String> parameters;

    /**
     * 是否是默认的
     */
    private Boolean isDefault;

    /**
     * 是否允许在注册中心订阅服务
     */
    private Boolean subscribe;

    public Boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Boolean isRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


}
