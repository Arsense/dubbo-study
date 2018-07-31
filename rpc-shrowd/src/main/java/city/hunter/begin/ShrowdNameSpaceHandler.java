package city.hunter.begin;

import city.hunter.weapon.ConsumerConfig;
import city.hunter.weapon.ProviderConfig;
import city.hunter.weapon.RegistryConfig;
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
        //这里备用吧
//        registerBeanDefinitionParser("monster",  new ShrowdBeanDefinationParser());
        //这里装载配置类 后面可以直接写死
        registerBeanDefinitionParser("register"
                ,new ShrowdBeanDefinationParser(RegistryConfig.class));
        //一个注册者 函数在注册中心注册加载
        registerBeanDefinitionParser("provider"
                ,new ShrowdBeanDefinationParser(ProviderConfig.class));
        //消费者在注册中订阅 并启动监听模式
        registerBeanDefinitionParser("consumer"
                ,new ShrowdBeanDefinationParser(ConsumerConfig.class));


        //装载
    }

}
