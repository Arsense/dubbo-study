package com.tw.dubbo.rpc.protocol.injvm;

import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Invoker;
import com.tw.dubbo.rpc.Protocol;
import com.tw.dubbo.rpc.ProxyFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * @author clay
 * @date 2020/2/2 17:04
 */
public class ProtocolTest {

    private ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    private Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();


    static {
        InjvmProtocol injvm = InjvmProtocol.getInjvmProtocol();
    }


    @Test
    public void testLocalProtocol() throws Exception {
        String INTERFACE_KEY = "interface";

        DemoService service = new DemoServiceImpl();
        //获取代理类
        Invoker<?> invoker = proxy.getInvoker(service, DemoService.class,
                URL.valueOf("injvm://127.0.0.1/TestService")
                    .addParameter(INTERFACE_KEY, DemoService.class.getName()));
        assertTrue(invoker.isAvailable());
        //本地发布暴露
        Exporter<?> exporter = protocol.export(invoker);
        //放入容器汇总
        exporters.add(exporter);


    }
}
