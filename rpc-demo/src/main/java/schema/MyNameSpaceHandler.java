package schema;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author tangwei
 * @date 2018/7/26 15:15
 */
public class MyNameSpaceHandler extends NamespaceHandlerSupport {


    public void init() {
        registerBeanDefinitionParser("people", (BeanDefinitionParser) new People());
    }
}
