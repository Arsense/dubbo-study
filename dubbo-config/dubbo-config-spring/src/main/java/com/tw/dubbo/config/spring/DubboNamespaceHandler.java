package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ConsumerConfig;
import com.tw.dubbo.config.ProtocolConfig;
import com.tw.dubbo.config.ProviderConfig;
import com.tw.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * <pre>
 *     自定义xml读取配置bean配置 这里是注册
 * </pre>
 *
 * @author clay
 * @date 2018/11/27 13:57
 */
public class DubboNamespaceHandler  extends NamespaceHandlerSupport {

    @Override
    public void init() {
        //提供者
        registerBeanDefinitionParser("provider", new DubboBeanDefinitionParser(ProviderConfig.class, true));
        //消费者
        registerBeanDefinitionParser("consumer", new DubboBeanDefinitionParser(ConsumerConfig.class, true));
        //provider配置里面
        registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
        //注册中心 集群配置
        registerBeanDefinitionParser("registry", new DubboBeanDefinitionParser(RegistryConfig.class, true));
        //RPC协议配置
        registerBeanDefinitionParser("protocol", new DubboBeanDefinitionParser(ProtocolConfig.class, true));
    }
}
