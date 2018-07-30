package schema;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author tangwei
 * @date 2018/7/26 15:15
 *
 * 这个类应该是绑定解析与要解析的类
 */
public class MyNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("people",  new PeopleBeanDefinationParser());
    }

}
