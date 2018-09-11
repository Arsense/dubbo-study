package com.remote.test.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author tangwei
 * @date 2018/9/11 19:44
 */
public class RevokerProxyBeanFactory implements InvocationHandler {

    private Class<?> targetInterface;
    //超时时间
    private int consumeTimeout = 1000;
    //调用者线程数
    private static int threadWorkerNumber = 10;
    //负载均衡策略
    private String clusterStrategy;


    /**
     * 实现代理
     * @return
     */
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{targetInterface}, this);
    }
    /**
     * 构造函数
     */
    RevokerProxyBeanFactory(Class<?> targetInterface , String clusterStrategy ){
        this.clusterStrategy = clusterStrategy;
        this.targetInterface = targetInterface;
    }


    /**
     * 调用服务方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }



}
