package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ConsumerConfig;
import com.tw.dubbo.config.ProviderConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * <pre>
 *     自定义xml读取配置bean配置 这里是注册
 * </pre>
 *
 * @author tangwei
 * @date 2018/11/27 13:57
 */
public class DubboNamespaceHandler  extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("provider", new DubboBeanDefinitionParser(ProviderConfig.class, true));
        registerBeanDefinitionParser("consumer", new DubboBeanDefinitionParser(ConsumerConfig.class, true));
    }
}
