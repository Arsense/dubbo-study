package com.tw.dubbo.common.config;

/**
 * @author clay
 * @date 2018/11/27 14:03
 */
public class ConsumerConfig  extends AbstractConfig{


    /**
     * Whether to use the default protocol
     */
    private Boolean isDefault;

    /**
     * Networking framework client uses: netty, mina, etc.
     */
    private String client;

    /**
     * Consumer thread pool type: cached, fixed, limit, eager
     */
    private String threadpool;

    /**
     * Consumer threadpool core thread size
     */
    private Integer corethreads;

    /**
     * Consumer threadpool thread size
     */
    private Integer threads;

    /**
     * Consumer threadpool queue size
     */
    private Integer queues;

    /**
     * By default, a TCP long-connection communication is shared between the consumer process and the provider process.
     * This property can be set to share multiple TCP long-connection communications. Note that only the dubbo protocol takes effect.
     */
    private Integer shareconnections;

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public Integer getQueues() {
        return queues;
    }

    public void setQueues(Integer queues) {
        this.queues = queues;
    }

    public Integer getShareconnections() {
        return shareconnections;
    }

    public void setShareconnections(Integer shareconnections) {
        this.shareconnections = shareconnections;
    }
}
