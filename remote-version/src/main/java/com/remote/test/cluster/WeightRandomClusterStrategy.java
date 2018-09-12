package com.remote.test.cluster;

import com.remote.test.provider.ProviderService;
import org.apache.commons.lang3.RandomUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2018/9/12 9:52
 */
public class WeightRandomClusterStrategy implements  ClusterStrategy{

    //权重是会根据负载大小变化的，如果负载一直增加，那么权重就会一直减少到不是最大，也就不会再分配任务给它了，反而分配给新的权重最大的服务器
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        List<ProviderService> providerList = new ArrayList<ProviderService>();
        for (ProviderService provider : providerServices) {
            //默认先写2吧 不知道有什么用
            int weight = 2;
            for (int i = 0; i < weight; i++) {
                providerList.add(provider.copy());

            }
        }
        int MAX_LEN = providerList.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerList.get(index);
    }
}
