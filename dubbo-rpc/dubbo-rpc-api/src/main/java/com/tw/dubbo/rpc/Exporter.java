package com.tw.dubbo.rpc;

/**
 * 发布类
 *
 *
 * @author clay
 * @date 2018/11/30 14:56
 */
public interface Exporter<T> {

    /**
     * get invoker.
     * @return invoker
     */
    Invoker<T> getInvoker();

    /**
     * unexport.
     * <p>
     * <code>
     * getInvoker().destroy();
     * </code>
     */
    void unexport();



}
