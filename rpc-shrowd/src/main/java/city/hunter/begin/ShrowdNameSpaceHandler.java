package city.hunter.begin;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author tangwei
 * @date 2018/7/26 15:15
 *
 * 这个类应该是绑定解析与要解析的类
 */
public class ShrowdNameSpaceHandler extends NamespaceHandlerSupport {

    static{
        //备用来 检查重复什么的
    }

    public void init() {

        registerBeanDefinitionParser("monster",  new PeopleBeanDefinationParser());
    }

}
