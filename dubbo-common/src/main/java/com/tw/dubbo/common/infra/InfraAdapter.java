package com.tw.dubbo.common.infra;

import com.tw.dubbo.common.extension.SPI;

import java.util.*;
/**

 *用于与其他系统交互。典型用例包括：

 *一。从与Dubbo当前部署的实例相关的底层基础结构中获取额外属性。

 *2。从第三方系统获取可能对特定组件有用的配置
 *
 * @author tangwei
 * @date 2020/1/26 15:06
 */
@SPI
public interface InfraAdapter {

    /**
     * get extra attributes
     *
     * @param params application name or hostname are most likely to be used as input params.
     * @return
     */
    Map<String, String> getExtraAttributes(Map<String, String> params);


    /**
     * @param key
     * @return
     */
    String getAttribute(String key);


}
