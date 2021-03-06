package com.tw.dubbo.rpc;

import com.tw.dubbo.common.extension.SPI;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2018/11/30 15:12
 */
@SPI("javassist")
public interface ProxyFactory {


    /**
     * 获取代理？
     * @param invoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException;



    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException;
}
