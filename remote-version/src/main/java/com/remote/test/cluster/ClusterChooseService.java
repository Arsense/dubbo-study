package com.remote.test.cluster;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/9/12 10:05
 */
public class ClusterChooseService {

    private static Map<String , String> ClusterStrategyMap  = new HashMap<String, String>();
    public static ClusterStrategy matchClusterStrategy(String clusterType){


        return new WeightRandomClusterStrategy();
    }
}
