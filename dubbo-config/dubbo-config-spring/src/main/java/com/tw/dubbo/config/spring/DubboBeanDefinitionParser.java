package com.tw.dubbo.config.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * <pre>
 *     这里解析xml配置成bean
 * </pre>
 * @author tangwei
 * @date 2018/11/27 13:59
 */
public class DubboBeanDefinitionParser implements BeanDefinitionParser {


    private final Class<?> beanClass;
    private final boolean required;
    /**
     * 这里使用泛型
     * @param beanClass
     * @param required
     */
    public DubboBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }


    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}
