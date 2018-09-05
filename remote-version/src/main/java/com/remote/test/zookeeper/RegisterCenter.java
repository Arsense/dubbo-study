package com.remote.test.zookeeper;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.remote.test.provider.ProviderService;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.*;

import org.apache.commons.collections.MapUtils;
/**
 * @author tangwei
 * @date 2018/8/31 14:36
 */
public class RegisterCenter implements ServiceRegistryCenter,ConsumeRegistryCenter {

    private final static Logger LOG = LoggerFactory.getLogger(RegisterCenter.class);

    //服务提供者列表,Key:服务提供者接口  value:服务提供者服务方法列表
    private static final Map<String,List<ProviderService>> serviceProviderMap  = new HashMap<String, List<ProviderService>>();
    //服务端ZK服务元信息,选择服务(第一次直接从ZK拉取,后续由ZK的监听机制主动更新)
    private static final Map<String,List<ProviderService>> zooKeeperServiceProviderMap = new HashMap<String, List<ProviderService>>();

    private static RegisterCenter registerCenter = new RegisterCenter();
    private static  Map<String, List<ProviderService>> registerProviderMap = new HashMap<String, List<ProviderService>>();
    public static RegisterCenter singleton() {
        return registerCenter;
    }
    private static ZkClient zooKeeperClient = null;


    private String ZK_SERVICE ="localhost:2181";

    private static final int SESSION_TIMEOUT = 500;
    private static final int CONNECTION_TIMEOUT = 500;
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

        if (zooKeeperClient == null) {
            zooKeeperClient = new ZkClient(ZK_SERVICE , SESSION_TIMEOUT , CONNECTION_TIMEOUT , new SerializableSerializer());
        }

        //创建 ZK命名空间/当前部署应用APP命名空间/
        String appKey = serviceList.get(0).getAppKey();
        String path = "/Clay";
        if (!zooKeeperClient.exists(path)) {
            zooKeeperClient.createPersistent(path,true);
        }

        for (Map.Entry<String , List<ProviderService>> entry : serviceProviderMap.entrySet()) {
            //服务分组
            String groupName = "/default";
            String serviceNode = entry.getKey();
            String serivePath = path + groupName + "/" + serviceNode;
            // Clay/default/com.remote.test.TestService
            if (!zooKeeperClient.exists(serivePath)) {
                zooKeeperClient.createPersistent(serivePath,true);
            }
            //然后创建当前服务节点
            String port = entry.getValue().get(0).getPort();
            String currentServiceIpPort = serivePath + "/127.0.0.1" + "|" + port + "|10";
            // /Clay/default/com.remote.test.TestService/127.0.0.1|8081|10
            if (!zooKeeperClient.exists(currentServiceIpPort)) {
                zooKeeperClient.createEphemeral(currentServiceIpPort);
            }
            //监听注册服务的变化,同时更新数据到本地缓存
            zooKeeperClient.subscribeChildChanges(serivePath, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

                    if (currentChilds == null) {
                        currentChilds = new ArrayList<String>();
                    }

                    //获取存活的IP列表
                    List<String> aliveServiceIpList =  Lists.newArrayList(Lists.transform(currentChilds, new Function<String, String>() {
                        @Override
                        public String apply(String input) {
                            return StringUtils.split(input, "|")[0];
                        }
                    }));
                    refreshAliveService(aliveServiceIpList);
                }
            });

        }


    }

    //利用ZK自动刷新当前存活的服务提供者列表数据
    private void refreshAliveService(List<String> aliveServices) {
        if (aliveServices == null) {
            aliveServices = Lists.newArrayList();
        }

        Map<String, List<ProviderService>> currentServiceMetaDataMap = Maps.newHashMap();
        for (Map.Entry<String, List<ProviderService>> entry : serviceProviderMap.entrySet()) {
            String key = entry.getKey();
            List<ProviderService> providerServices = entry.getValue();

            List<ProviderService> serviceMetaDataModelList = currentServiceMetaDataMap.get(key);
            if (serviceMetaDataModelList == null) {
                serviceMetaDataModelList = Lists.newArrayList();
            }

            for (ProviderService serviceMetaData : providerServices) {
                if (aliveServices.contains("127.0.0.1")) {
                    serviceMetaDataModelList.add(serviceMetaData);
                }
            }
            currentServiceMetaDataMap.put(key, serviceMetaDataModelList);
        }
        aliveServices.clear();
        System.out.println("currentServiceMetaDataMap,"+ JSON.toJSONString(currentServiceMetaDataMap));

        serviceProviderMap.putAll(currentServiceMetaDataMap);
    }


    public Map<String, List<ProviderService>> getRegisterProviderMap() {
        return registerProviderMap;
    }


    @Override
    public void initProviderServiceMap(String appKey, String groupName) {
        if (MapUtils.isNotEmpty(serviceProviderMap)) {
            return;
        }
        if (zooKeeperClient == null) {
            zooKeeperClient = new ZkClient(ZK_SERVICE , SESSION_TIMEOUT , CONNECTION_TIMEOUT , new SerializableSerializer());
        }
        String providerPath = "/Clay/default";
        List<String> serivceList = zooKeeperClient.getChildren(providerPath);
        if (CollectionUtils.isEmpty(serivceList)) {
            throw new RuntimeException("获取服务失败");
        }
        //构建service路径
        for (String serviceName : serivceList) {
         String servicePath = providerPath + "/" + serviceName;

         List<String>  serviceConfig = zooKeeperClient.getChildren(servicePath);

         if (CollectionUtils.isEmpty(serviceConfig)) {
             LOG.error("获取该节点service失败 {}" ,servicePath);
             continue;
         }
         for(String receiveData : serviceConfig ) {
             String serverIp = receiveData.split("\\|")[0];
             String port = receiveData.split("\\|")[1];
             int workThreads = Integer.parseInt(receiveData.split("\\|")[2]);

             List<ProviderService> providerServices = serviceProviderMap.get(receiveData);
             if (CollectionUtils.isEmpty(providerServices)) {
                 providerServices = new ArrayList<ProviderService>();
             }
             ProviderService providerService = new ProviderService();
             try {
                 providerService.setServiceInterface(ClassUtils.getClass(serviceName));
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }
             providerService.setServerIp(serverIp);
             providerService.setTimeout(500);
             providerService.setPort(port);
             providerService.setWorkThreads(workThreads);
             providerServices.add(providerService);

             zooKeeperServiceProviderMap.put(serviceName , providerServices);
         }
            //监听注册服务的变化,同时更新数据到本地缓存
            zooKeeperClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    if (currentChilds == null) {
                        currentChilds = Lists.newArrayList();
                    }
                    currentChilds = Lists.newArrayList(Lists.transform(currentChilds, new Function<String, String>() {
                        @Override
                        public String apply(String input) {
                            return org.apache.commons.lang3.StringUtils.split(input, "|")[0];
                        }
                    }));
                    refreshAliveService(currentChilds);
                }
            });


        }
    }

    @Override
    public Map<String, List<ProviderService>> getProviderServicesToConsume() {
        return zooKeeperServiceProviderMap;
    }

}
