package com.remote.test.zookeeper;

import com.remote.test.service.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/9/4 14:58
 */
public interface ConsumeRegistryCenter {

    /**
     * 消费端初始化服务提供者消息到本地
     * @param appKey
     * @param groupName
     */
    public void initProviderServiceMap(String appKey , String groupName);


    /**
     * 获取服务到本地
     */
    public Map<String, List<ProviderService>>  getProviderServicesToConsume ();

}
