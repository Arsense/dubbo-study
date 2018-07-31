package city.hunter.begin;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

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
//        beanDefinition.

        return beanDefinition;
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
