package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.ConfigCenterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author clay
 * @date 2020/2/2 16:24
 */
public class ConfigCenterConfigTest {

    @Test
    public void testPrefix() {
        ConfigCenterConfig config = new ConfigCenterConfig();
        Assertions.assertEquals("dubbo.config-center", config.getPrefix());
    }

    @Test
    public void testToUrl() {
        ConfigCenterConfig config = new ConfigCenterConfig();
        config.setNamespace("namespace");
        config.setGroup("group");
        config.setAddress("zookeeper://127.0.0.1:2181");

        Assertions.assertEquals("zookeeper://127.0.0.1:2181/ConfigCenterConfig?check=true&" +
                        "config-file=dubbo.properties&group=group&highest-priority=true&" +
                        "namespace=namespace&timeout=3000",
                config.toUrl().toFullString()
        );
    }


}
