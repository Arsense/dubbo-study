package league.of.legends;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author tangwei
 * @date 2018/8/3 16:59
 */
public class ShopNamespaceHandler  extends NamespaceHandlerSupport {


    public void init() {
        registerBeanDefinitionParser("test",new ShopBeanDefinitionParser());
    }
}
