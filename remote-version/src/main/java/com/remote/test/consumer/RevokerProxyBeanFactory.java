package com.remote.test.consumer;

import com.remote.test.cluster.ClusterChooseService;
import com.remote.test.cluster.ClusterStrategy;
import com.remote.test.provider.ProviderService;
import com.remote.test.zookeeper.RegisterCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

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
    private String clusterStrategy = "WeightRandom";


    /**
     * 实现代理
     * @return
     */
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{targetInterface}, this);
    }
    /**
     * 负载均衡先不管
     * 构造函数
     */
    RevokerProxyBeanFactory(Class<?> targetInterface , String clusterStrategy ){
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
       //根据接口名获取服务提供者列表 然后 负载均衡知道本次的方法和接口 发起调用该
        String interfaceName = targetInterface.getName();

        RegisterCenter registerCenter = RegisterCenter.singleton();
        //获取接口列表
        List<ProviderService> providerServices = (List<ProviderService>) registerCenter.getProviderServicesToConsume().get(interfaceName);
        //根据软负载策略,从服务提供者列表选取本次调用的服务提供者
        ClusterStrategy strategy = ClusterChooseService.matchClusterStrategy(clusterStrategy);
        ProviderService providerService = strategy.select(providerServices);
        //复制一份服务提供者信息
        ProviderService newProvider = providerService.copy();



        return null;
    }



}
