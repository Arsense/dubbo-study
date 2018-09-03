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

    /**
     * 这里重写getBeanClass 在AbstractSingleBeanDefinitionParser调用parseInternal 会加入到Bean容器中
     * @param element
     * @return
     */
    protected Class getBeanClass(Element element) {
        return ProviderFactoryBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        //配置属性的读取

        try {
            String serviceInterface = element.getAttribute("interface");
            String port = element.getAttribute("port");
            String ref = element.getAttribute("ref");
            String appKey = element.getAttribute("appKey");
            //这里要与ProviderFactoryBean中变量名一一对应
            bean.addPropertyValue("serviceInterface", serviceInterface);
            bean.addPropertyValue("port", port);
            bean.addPropertyReference("serviceObject", ref);
            bean.addPropertyValue("appKey", appKey);


        } catch (Exception e){
            LOG.error("xml 读取配置失败");
            e.printStackTrace();
        }

    }





}
