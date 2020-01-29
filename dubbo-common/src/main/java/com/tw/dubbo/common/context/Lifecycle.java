package com.tw.dubbo.common.context;

/**
 *
 * dubbo生命周期接口类
 * @author clay
 * @date 2020/1/29 22:58
 */
public interface Lifecycle {

    /** Initialize the component before {@link #start() start}
     *
             * @return current {@link Lifecycle}
     * @throws IllegalStateException
     */
    void initialize() throws IllegalStateException;

    /**
     * Start the component
     *
     * @return current {@link Lifecycle}
     * @throws IllegalStateException
     */
    void start() throws IllegalStateException;

    /**
     * Destroy the component
     *
     * @throws IllegalStateException
     */
    void destroy() throws IllegalStateException;


}
