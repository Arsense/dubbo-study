package com.remote.test.cluster;

import com.remote.test.provider.ProviderService;
import java.util.List;

/**
 * @author tangwei
 * @date 2018/9/12 9:44
 */
public interface ClusterStrategy {
    /**
     * 负载策略算法
     *
     * @param providerServices
     * @return
     */
    public ProviderService select(List<ProviderService> providerServices);

}
