package com.tw.dubbo.rpc;

/**
 * @author tangwei
 * @date 2018/11/29 14:58
 */
public interface Protocol {


    /**
     * *当用户未配置端口时获取默认端口。
     *
     * @return
     */
    int getDefaultPort();

    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;
}
