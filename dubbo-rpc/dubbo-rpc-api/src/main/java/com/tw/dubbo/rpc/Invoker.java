package com.tw.dubbo.rpc;

import com.tw.dubbo.common.Node;
import com.tw.dubbo.common.utils.URL;

/**
 * 反射类 (API/SPI, Prototype, ThreadSafe)
 *
 * @author clay
 * @date 2018/11/30 14:57
 */
public interface Invoker<T> extends Node {

    /**
     * get service interface.
     *
     * @return service interface.
     */
    Class<T> getInterface();

    /**
     * invoke.
     *
     * @param invocation
     * @return result
     * @throws RpcException
     */
    Result invoke(Invocation invocation) throws RpcException;



    /**
     * 协议的URL地址
     *
     * @return url.
     */
    URL getUrl();
}
