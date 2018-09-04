package com.remote.test.service;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author tangwei
 * @date 2018/9/4 12:32
 */
public class ConsumerBeanDefinitionParser  extends AbstractSingleBeanDefinitionParser{
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {



    }
    @Override
    protected Class<?> getBeanClass(Element element) {
        return RevokerFactoryBean.class;
    }


}
