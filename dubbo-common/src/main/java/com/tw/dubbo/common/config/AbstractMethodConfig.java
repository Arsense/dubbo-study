package com.tw.dubbo.common.config;


import java.util.*;
/**
 * @author clay
 * @date 2020/1/29 12:15
 */
public abstract class AbstractMethodConfig extends AbstractConfig {

    protected Integer timeout;

    /**
     * The customized parameters
     */
    protected Map<String, String> parameters;

    /**
     * 重试次数
     */
    protected Integer retries;

    /**
     * Forks for forking cluster
     */
    protected Integer forks;

    /**
     * 负载均衡
     */
    protected String loadbalance;



    /**
     * max concurrent invocations
     */
    protected Integer actives;

    /**
     * 是否异步
     * 注意：这是一种不可靠的异步机制，它忽略返回值并且不阻塞线程。
     */
    protected Boolean async;


    /**
     * 以call参数为key缓存返回结果，提供以下选项：lru、threadlocal，
     *jcache等。
     */
    protected String cache;

    /**
     * 当服务无法执行时调用的模拟类的名称
     *注意：mock在提供者端不支持，当非业务异常时执行mock
     *在远程服务调用后发生
     */
    protected String mock;

    /**
     * Merger 啥子用
     */
    protected String merger;

    public String getMerger() {
        return merger;
    }

    public void setMerger(String merger) {
        this.merger = merger;
    }

    public Boolean getAsync() {
        return async;
    }

    public String getMock() {
        return mock;
    }

    public void setMock(String mock) {
        if (mock == null) {
            return;
        }
        this.mock = mock;
    }

    public void setMock(Boolean mock) {
        if (mock == null) {
            setMock((String) null);
        } else {
            setMock(mock.toString());
        }
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Integer getActives() {
        return actives;
    }

    public void setActives(Integer actives) {
        this.actives = actives;
    }

    public Boolean isAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}
