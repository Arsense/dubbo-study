package com.tw.dubbo.rpc;

/**
 * 反射类
 *
 * @author tangwei
 * @date 2018/11/30 14:57
 */
public interface Invoker<T>  {

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
}
