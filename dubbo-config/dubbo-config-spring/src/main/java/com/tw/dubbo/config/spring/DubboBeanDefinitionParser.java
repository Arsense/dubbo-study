package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.util.StringUtils;
import com.tw.dubbo.common.config.ProtocolConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *     这里解析xml配置成bean
 * </pre>
 * @author clay
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

    //其实整个原理如  bean.addPropertyValue("serviceItf", Class.forName(serviceItf));
    //              String serviceItf = element.getAttribute("interface");
    //这里是先把property遍历生成 然后去 遍历attibut匹配赋值的

    /**
     * 从xml获取值配置到Bean中
     * @param element
     * @param parserContext
     * @param beanClass
     * @param required
     * @return
     */
    private static BeanDefinition parse(Element element, ParserContext parserContext
            , Class<?> beanClass, boolean required){
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        //设置懒加载 false就是立即加载beanDefinition
        beanDefinition.setLazyInit(false);

        if("dubbo:protocol".equals(element.getTagName())){
            int a = 1;
        }

        //从xml取出 id=""后面的值
        String id = element.getAttribute("id");
        //没有Id 可能是<dubbo:protocol name="dubbo" port="20880"/>
        //或者是  <dubbo:application name="demo-provider"/>
        if ((id == null || id.length() == 0) && required) {
            String generatedBeanName = element.getAttribute("name");
            //name 也没有 基本可能是interface配置了
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = element.getAttribute("interface");
            }
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = beanClass.getName();
            }
            id = generatedBeanName;
        }
//      <bean id="demoService" class="org.apache.dubbo.demo.provider.DemoServiceImpl"/>

        //这里注册到beanNames当中
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.getPropertyValues().addPropertyValue("id", id);

        if (ProtocolConfig.class.equals(beanClass)) {
            //如果是协议配置
        } else if(ServiceBean.class.equals(beanClass)){
            //ServiceBean如果指定了类就加载注册 没有就是接口不处理
            String className = element.getAttribute("class");
        }
        //所有属性的集合
        Set<String> props = new HashSet<String>();
        //把ServiceBean中所有的方法放进去 props里面 后面好匹配遍历
        ManagedMap parameters = null;
        for (Method setter : beanClass.getMethods()) {
            String name = setter.getName();
            //如果有私有属性会报错吧
            if(name.length() > 3 && name.startsWith("set")
//                    && Modifier.isPublic(setter.getModifiers())
                    && setter.getParameterTypes().length == 1){
                Class<?> type = setter.getParameterTypes()[0];
                //取出 setAge 后面age
                String property = StringUtils.camelToSplitName(name.substring(3, 4).toLowerCase() + name.substring(4), "-");
                props.add(property);
                //设置setter
                Method getter = null;
                try {
                    getter = beanClass.getMethod("get" + name.substring(3), new Class<?>[0]);
                } catch (NoSuchMethodException e) {
                    try {
                        getter = beanClass.getMethod("is" + name.substring(3), new Class<?>[0]);
                    } catch (NoSuchMethodException e2) {
                    }
                }
                if (getter == null
                        || !type.equals(getter.getReturnType())) {
                    continue;
                }

                //关键的设置属性的步骤
                String value = element.getAttribute(property);
                if (value != null) {
                    value = value.trim();
                    if (value.length() > 0) {
                       Object reference;
                       reference = value;
                       //主要设置属性走这
                       beanDefinition.getPropertyValues().addPropertyValue(property, reference);
                    }
                }

            }
        }
        NamedNodeMap attributes = element.getAttributes();

        int len = attributes.getLength();
        for (int i = 0; i < len; i++) {
            Node node = attributes.item(i);
            String name = node.getLocalName();
            if (!props.contains(name)) {
//                if (parameters == null) {
//                    parameters = new ManagedMap();
//                }
//                //把属性中配了值的放到bean的 相关map中去
//                String value = node.getNodeValue();
//                parameters.put(name, new TypedStringValue(value, String.class));
            }
        }
        if (parameters != null) {
            beanDefinition.getPropertyValues().addPropertyValue("parameters", parameters);
        }
        return beanDefinition;
    }


}
