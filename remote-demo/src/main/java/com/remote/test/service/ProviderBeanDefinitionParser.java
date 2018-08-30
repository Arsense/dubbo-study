package com.remote.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author tangwei
 * @date 2018/8/30 17:09
 */
public class ProviderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    //  日志输出
    private static final Logger LOG = LoggerFactory.getLogger(ProviderBeanDefinitionParser.class);
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        //配置属性的读取

        try {
            String interfaceName = element.getAttribute("interface");
            String port = element.getAttribute("port");

        } catch (Exception e){
            e.printStackTrace();
        }

    }





}
