package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.AbstractInterfaceConfig;
import com.tw.dubbo.common.config.ApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author clay
 * @date 2020/1/29 12:22
 */
public class AbstractInterfaceConfigTest {


    /**
     * 校验注册信息
     */
    @Test
    public void testCheckRegistry1() {
        System.setProperty("dubbo.registry.address", "addr1");
        try {
            InterfaceConfig interfaceConfig = new InterfaceConfig();
            interfaceConfig.setApplication(new ApplicationConfig("testCheckRegistry1"));
            interfaceConfig.checkRegistry();
            Assertions.assertEquals(1, interfaceConfig.getRegistries().size());
            Assertions.assertEquals("addr1", interfaceConfig.getRegistries().get(0).getAddress());
        } finally {
            System.clearProperty("dubbo.registry.address");
        }
    }



    @Test
    public void testCheckRegistry2() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            InterfaceConfig interfaceConfig = new InterfaceConfig();
            interfaceConfig.checkRegistry();
        });
    }



    public static class InterfaceConfig extends AbstractInterfaceConfig {

    }
}
