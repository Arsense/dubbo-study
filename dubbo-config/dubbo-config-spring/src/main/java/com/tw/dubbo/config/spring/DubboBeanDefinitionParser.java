package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ProtocolConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
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

    /**
     * 解析加载xml配置到Spring 的Bean中
     * @param element
     * @param parserContext
     * @return
     */
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }


    private static BeanDefinition parse(Element element, ParserContext parserContext
            , Class<?> beanClass, boolean required){
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        //设置懒加载 false就是立即加载beanDefinition
        beanDefinition.setLazyInit(false);
        //从xml取出 id=""后面的值
        String id = element.getAttribute("id");
        //没有Id 可能是<dubbo:protocol name="dubbo" port="20880"/>
        //或者是  <dubbo:application name="demo-provider"/>
        if ((id == null || id.length() == 0) && required) {
            String generatedBeanName = element.getAttribute("name");
            //name 也没有 基本可能是interface配置了
            if (generatedBeanName == null || generatedBeanName.length() == 0) {

            }
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = beanClass.getName();
            }
        }
//      <bean id="demoService" class="org.apache.dubbo.demo.provider.DemoServiceImpl"/>

        if (id != null && id.length() > 0) {

        }
        if (ProtocolConfig.class.equals(beanClass)) {
            //如果是协议配置
        } else if(ServiceBean.class.equals(beanClass)){
            //ServiceBean如果指定了类就加载注册 没有就是接口不处理
        }

            return null;
    }
}
