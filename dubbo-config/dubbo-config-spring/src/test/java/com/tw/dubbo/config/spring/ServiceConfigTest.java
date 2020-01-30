package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.*;
import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.config.ServiceConfig;
import com.tw.dubbo.config.spring.api.DemoService;
import com.tw.dubbo.config.spring.provider.impl.DemoServiceImpl;
import com.tw.dubbo.rpc.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.registry.Registry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author clay
 * @date 2020/1/22 23:27
 */
public class ServiceConfigTest {
    private Protocol protocolDelegate = Mockito.mock(Protocol.class);
    private Registry registryDelegate = Mockito.mock(Registry.class);

    private ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();


    @BeforeEach
    public void setUp() throws Exception {
//        MockProtocol2.delegate = protocolDelegate;
//        MockRegistryFactory2.registry = registryDelegate;
        ApplicationConfig app = new ApplicationConfig("app");

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("mockprotocol2");

        ProviderConfig provider = new ProviderConfig();
        provider.setExport(true);
        provider.setProtocol(protocolConfig);


        RegistryConfig registry = new RegistryConfig();
//        registry.setProtocol("mockprotocol2");
//        registry.setAddress("N/A");

        //设置提供者 app 注册信息 接口名 接口实现
//        service.setProvider(provider);
//        service.setApplication(app);
        service.setRegistry(registry);
//        service.setInterface(DemoService.class);
//        service.setRef(new DemoServiceImpl());

    }

    /**
     * 测试服务端接口发布
     * @throws Exception
     */
    @Test
    public void testExport() throws Exception {
        service.export();
//        URL url = service.toUrl();
        URL url = new URL();
        assertThat(url.getProtocol(), equalTo("mockprotocol2"));
        assertThat(url.getPath(), equalTo(DemoService.class.getName()));
//        assertThat(url.getParameters(), hasEntry(ANYHOST_KEY, "true"));
//        assertThat(url.getParameters(), hasEntry(APPLICATION_KEY, "app"));
//        assertThat(url.getParameters(), hasKey(BIND_IP_KEY));
//        assertThat(url.getParameters(), hasKey(BIND_PORT_KEY));
//        assertThat(url.getParameters(), hasEntry(EXPORT_KEY, "true"));
//        assertThat(url.getParameters(), hasEntry("echo.0.callback", "false"));
//        assertThat(url.getParameters(), hasEntry(GENERIC_KEY, "false"));
//        assertThat(url.getParameters(), hasEntry(INTERFACE_KEY, DemoService.class.getName()));
//        assertThat(url.getParameters(), hasKey(METHODS_KEY));
//        assertThat(url.getParameters().get(METHODS_KEY), containsString("echo"));
//        assertThat(url.getParameters(), hasEntry(SIDE_KEY, PROVIDER));
//        Mockito.verify(protocolDelegate).export(Mockito.any(Invoker.class));

    }
}
