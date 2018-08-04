package league.of.legends;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;


/**
 * @author tangwei
 * @date 2018/8/3 17:04
 */
public class ShopBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    protected  Class getBeanClass(Element element) {
        return AttackFactoryBean.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        //为空做什么处理
        String interfaceName = element.getAttribute("interface");
        String port = element.getAttribute("port");
        String timeout = element.getAttribute("timeout");

        try {
            bean.addPropertyValue("serviceInterface",Class.forName(interfaceName));
            bean.addPropertyValue("port",Integer.parseInt(port));
            bean.addPropertyValue("timeout",Integer.parseInt(timeout));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("serviceConfig 异常");
        }


    }



}
