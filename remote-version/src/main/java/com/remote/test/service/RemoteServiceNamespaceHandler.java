package com.remote.test.service;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author tangwei
 * @date 2018/8/30 17:08
 */
public class RemoteServiceNamespaceHandler extends NamespaceHandlerSupport{
    public void init() {
        registerBeanDefinitionParser("service",  new ProviderBeanDefinitionParser());
        registerBeanDefinitionParser("client", new ConsumerBeanDefinitionParser());
    }
}
