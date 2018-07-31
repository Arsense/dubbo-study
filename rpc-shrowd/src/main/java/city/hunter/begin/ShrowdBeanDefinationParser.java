package city.hunter.begin;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import java.util.HashSet;
import java.util.Set;

/**  这里对应解析装载 xml的配置类 并加入到相应的
 * @author tangwei
 * @date 2018/7/30 10:49
 */
public class ShrowdBeanDefinationParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    ShrowdBeanDefinationParser(Class<?> beanClass){
        this.beanClass = beanClass;
    }


    public BeanDefinition parse(Element element, ParserContext parserContext,Class<?> beanClass) {

        //用来加载我们的配置类
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //解析相应的Bean 然后装载到Spring

        //先判断是否有ID 没有就自己设定
        String id = element.getAttribute("id");

        if (id == null ||id.length() == 0) {
            String initBeanName = element.getAttribute("name");
            if (initBeanName == null ||initBeanName.length() == 0) {
               // initBeanName = element.getAttribute("interface");
                //没有name 就用类名做beanName
                initBeanName = beanClass.getName();
            }
            id = initBeanName;
        }
        if (id != null && id.length() >0) {
            //获得属性的键值
            beanDefinition.getPropertyValues().addPropertyValue("id",id);
        }

        getBeanClassSetterAndGetter(beanDefinition,beanClass);

        return beanDefinition;
    }

    /**
     *
     * @param beanDefinition
     * @param beanClass
     */
    private void getBeanClassSetterAndGetter(RootBeanDefinition beanDefinition,Class<?> beanClass) {

        Set<String> props = new HashSet<String>();
        ManagedMap parameters = null;
        //这里是获取类的getter 和setter方法
//        for ()

    }


    /**
     * 重写的解析函数
     * @param element
     * @param parserContext
     * @return
     */
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element,parserContext,beanClass);
    }
}
