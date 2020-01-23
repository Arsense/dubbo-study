package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ApplicationConfig;
import com.tw.dubbo.config.MonitorConfig;
import com.tw.dubbo.config.RegistryConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 *
 * 应用config测试类
 * @author tangwei
 * @date 2020/1/23 16:46
 */
public class ApplicationConfigTest {

    /**
     * 很简单的测试 测试应用名是否可以添加成功
     * tesk ok
     * @throws Exception
     */
    @Test
    public void testName() throws Exception{
        ApplicationConfig application = new ApplicationConfig();
        application.setName("app");
        assertThat(application.getName(), equalTo("app"));
        application = new ApplicationConfig("app2");
        assertThat(application.getName(), equalTo("app2"));
        Map<String, String> parameters = new HashMap<String, String>();
        ApplicationConfig.appendParameters(parameters, application);
        assertThat(parameters, hasEntry("application", "app2"));
    }


    /**
     * 版本测试
     * @throws Exception
     */
    @Test
    public void testVersion() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setVersion("1.0.0");
        assertThat(application.getVersion(), equalTo("1.0.0"));
        Map<String, String> parameters = new HashMap<String, String>();
        ApplicationConfig.appendParameters(parameters, application);
        assertThat(parameters, hasEntry("application.version", "1.0.0"));
    }


    @Test
    public void testOwner() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setOwner("owner");
        assertThat(application.getOwner(), equalTo("owner"));
    }


    @Test
    public void testOrganization() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setOrganization("org");
        assertThat(application.getOrganization(), equalTo("org"));
    }

    @Test
    public void testArchitecture() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setArchitecture("arch");
        assertThat(application.getArchitecture(), equalTo("arch"));
    }

    /**
     * 测试 线上环境测试
     * @throws Exception
     */
    @Test
    public void testEnvironment1() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setEnvironment("develop");
        assertThat(application.getEnvironment(), equalTo("develop"));
        application.setEnvironment("test");
        assertThat(application.getEnvironment(), equalTo("test"));
        application.setEnvironment("product");
        assertThat(application.getEnvironment(), equalTo("product"));
    }

    @Test
    public void testEnvironment2() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            ApplicationConfig application = new ApplicationConfig("app");
            application.setEnvironment("illegal-env");
        });
    }

    /**
     * 测试注册config设置
     * @throws Exception
     */
    @Test
    public void testRegistry() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        RegistryConfig registry = new RegistryConfig();
        application.setRegistry(registry);
        assertThat(application.getRegistry(), sameInstance(registry));
        application.setRegistries(Collections.singletonList(registry));
        assertThat(application.getRegistries(), contains(registry));
        assertThat(application.getRegistries(), hasSize(1));
    }

    @Test
    public void testMonitor() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setMonitor(new MonitorConfig("monitor-addr"));
        assertThat(application.getMonitor().getAddress(), equalTo("monitor-addr"));
        application.setMonitor("monitor-addr");
        assertThat(application.getMonitor().getAddress(), equalTo("monitor-addr"));
    }

    @Test
    public void testLogger() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setLogger("log4j");
        assertThat(application.getLogger(), equalTo("log4j"));
    }

    @Test
    public void testDefault() throws Exception {
        ApplicationConfig application = new ApplicationConfig("app");
        application.setDefault(true);
        assertThat(application.isDefault(), is(true));
    }
}
