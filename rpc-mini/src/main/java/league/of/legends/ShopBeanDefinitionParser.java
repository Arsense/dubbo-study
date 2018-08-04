package league.of.legends;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.security.spec.EllipticCurve;

/**
 * @author tangwei
 * @date 2018/8/3 17:04
 */
public class ShopBeanDefinitionParser implements BeanDefinitionParser {




    protected  Class getBeanClass(Element element) {
        return AttackFactoryBean.class;
    }
    public BeanDefinition parse(Element element, ParserContext parserContext) {


        return null;
    }

}
