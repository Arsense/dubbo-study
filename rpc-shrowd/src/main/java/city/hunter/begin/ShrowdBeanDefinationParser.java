package city.hunter.begin;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**  这里对应解析装载 xml的配置类 并加入到相应的
 * @author tangwei
 * @date 2018/7/30 10:49
 */
public class ShrowdBeanDefinationParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    ShrowdBeanDefinationParser(Class<?> beanClass){
        this.beanClass = beanClass;
    }


    private static BeanDefinition parse(Element element, ParserContext parserContext,Class<?> beanClass) {

        //用来加载我们的配置类
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //解析相应的Bean 然后装载到Spring
        beanDefinition.setBeanClass(beanClass);

        //设置懒加载为false，表示立即加载，spring启动时，立刻进行实例化
        //如果设置为true，那么要第一次向容器通过getBean索取bean时实例化，在spring bean的配置里可以配置
        //这里会设置懒加载为false其实还可以得到一个推断就是:dubbo标签创建的bean就是单例bean(singleton bean)
        //因为lazy-init的设置只对singleton bean有效，对原型bean(prototype无效)
        beanDefinition.setLazyInit(false);
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
            parserContext.getRegistry().registerBeanDefinition(id,beanDefinition);
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
    private static void getBeanClassSetterAndGetter(RootBeanDefinition beanDefinition, Class<?> beanClass) {

//        Set<String> props = new HashSet<String>();
        ManagedMap parameters = null;
        //这里是获取类的getter 和setter方法
        for (Method setter: beanClass.getMethods()){
            String name = setter.getName();
            //检验前三个字符是否是 setter 检验其属性 1表示public  检验返回值只有一个
            if (name.length() > 3 && name.startsWith("set")
                    && Modifier.isPublic(setter.getModifiers())
                    && setter.getParameterTypes().length == 1) {

                Class<?> type = setter.getParameterTypes()[0];
                //取出set后面的属性
//                String property = StringUtils.

                //获取方法
                Method getter = null;

                try {
                    getter = beanClass.getMethod("get" + name.substring(3), new Class<?>[0]);
                } catch (NoSuchMethodException e) {
                    try {
                        getter = beanClass.getMethod("is"+ name.substring(2), new Class<?>[0]);
                    }catch (NoSuchMethodException e1){
                        e1.printStackTrace();
                    }
                }
                // 如果只有setter 没有getter 直接跳过吗？
                if (getter == null || !Modifier.isPublic(getter.getModifiers())
                                   || !type.equals(getter.getReturnType())) {
                    continue;
                }

            }
        }

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
