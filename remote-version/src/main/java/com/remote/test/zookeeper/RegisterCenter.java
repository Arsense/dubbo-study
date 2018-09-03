package com.remote.test.zookeeper;

import com.remote.test.service.ProviderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/8/31 14:36
 */
public class RegisterCenter implements ServerRegisterCenter {

    private static RegisterCenter registerCenter = new RegisterCenter();

    private static  Map<String, List<ProviderService>> registerProviderMap = new HashMap<String, List<ProviderService>>();

    public static RegisterCenter singleton() {
        return registerCenter;
    }
    public void registerProvider(List<ProviderService> serviceList) {

    }

    public Map<String, List<ProviderService>> getRegisterProviderMap() {
        return registerProviderMap;
    }
}
