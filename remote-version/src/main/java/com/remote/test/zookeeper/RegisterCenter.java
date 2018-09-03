package com.remote.test.zookeeper;

import com.remote.test.service.ProviderService;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author tangwei
 * @date 2018/8/31 14:36
 */
public class RegisterCenter implements ServiceRegistryCenter {

    //服务提供者列表,Key:服务提供者接口  value:服务提供者服务方法列表
    private static final Map<String,List<ProviderService>> serviceProviderMap  = new HashMap<String, List<ProviderService>>();
    //服务端ZK服务元信息,选择服务(第一次直接从ZK拉取,后续由ZK的监听机制主动更新)
    private static final Map<String,List<ProviderService>> zooKeeperServiceProviderMap = new HashMap<String, List<ProviderService>>();

    private static RegisterCenter registerCenter = new RegisterCenter();
    private static  Map<String, List<ProviderService>> registerProviderMap = new HashMap<String, List<ProviderService>>();
    public static RegisterCenter singleton() {
        return registerCenter;
    }
    private static ZkClient zookeeperClient = null;

    /**
     * zk注册函数 用户现在只有我一个 先不加锁 看会出现什么错？
     * @param serviceList
     */
    @Override
    public void registerProvider(List<ProviderService> serviceList) {
        if(CollectionUtils.isEmpty(serviceList)) {
            return;
        }
        //存在则获取 不存在则添加
        for(ProviderService provider : serviceList) {
            String interfaceName = provider.getServiceInterface().getName();

            List<ProviderService> providers = serviceProviderMap.get(interfaceName);

            if (providers == null || providers.size() == 0) {
                providers = new ArrayList<ProviderService>();
            }
            providers.add(provider);
            serviceProviderMap.put(interfaceName , providers);
        }




    }

    public Map<String, List<ProviderService>> getRegisterProviderMap() {
        return registerProviderMap;
    }
}
