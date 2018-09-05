package com.remote.test.zookeeper;

import com.remote.test.service.ProviderService;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/8/31 14:32
 */
public interface ServiceRegistryCenter {


    /**
     *  服务端将服务提供者信息注册到zk对应的节点下
     * @param serviceList
     */
    public void registerProvider(final List<ProviderService> serviceList);


    /**
     * 服务端获取服务提供者信息
     * <p/>
     * 注:返回对象,Key:服务提供者接口  value:服务提供者服务方法列表
     *
     * @return
     */

    public Map<String, List<ProviderService>> getRegisterProviderMap();

}
