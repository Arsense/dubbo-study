package com.remote.test.consumer;

import com.remote.test.netty.NettyCosumeChannelQueue;
import com.remote.test.provider.ProviderService;
import com.remote.test.zookeeper.ConsumeRegistryCenter;
import com.remote.test.zookeeper.RegisterCenter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/9/4 12:36
 */
public class RevokerFactoryBean  implements FactoryBean, InitializingBean {

    //目标服务接口
    private Class<?> targetInterface;
    //唯一标识
    private String appKey;
    //服务Bean
    private Object serviceObject;
    //服务分组名
    private String groupName=  "default";
    //负载均衡策略
    private String clusterStrategy = "WeightRandom";


    @Override
    public Object getObject() throws Exception {
        return serviceObject;
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //获取服务注册中心
        ConsumeRegistryCenter registryCenter = RegisterCenter.singleton();
        //初始化服务提供者列表到本地
        registryCenter.initProviderServiceMap(appKey , groupName);
        Map<String, List<ProviderService>> providerMap = registryCenter.getProviderServicesToConsume();
        //初始化Netty Channel
        NettyCosumeChannelQueue.singleton().initChannelPoolFactory(providerMap);
        RevokerProxyBeanFactory proxyBeanFactory = new RevokerProxyBeanFactory(targetInterface , clusterStrategy);
        this.serviceObject = proxyBeanFactory.getProxy();

        //将消费者信息注册到注册中心
        ConsumeService consume =  new ConsumeService();
        consume.setServiceInterface(targetInterface);
        registryCenter.registerConsumer(consume);



    }


    public Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
