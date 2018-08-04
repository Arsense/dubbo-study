package league.of.legends;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 开启单例模式
 * attack Bean工厂 服务发布入口
 * @author tangwei
 * @date 2018/8/4 13:06
 */

//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
//FactoryBean 工厂Bean返回的是一个Object 不是一个特定的对象

public class AttackFactoryBean implements FactoryBean,InitializingBean{

    //服务接口
    private Class<?> serviceInterface;
    //服务的对象
    private Object serviceBean;
    //端口
    private String port;
    //设置短时间连接超时
    private long timeout;



    public void afterPropertiesSet() throws Exception {

    }

    public Object getObject() throws Exception {
        return null;
    }

    public Class<?> getObjectType() {
        return  serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }
}
