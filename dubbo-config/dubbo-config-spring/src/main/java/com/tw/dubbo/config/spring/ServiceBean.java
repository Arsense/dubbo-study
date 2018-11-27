package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author tangwei
 * @date 2018/11/27 14:53
 */
public class ServiceBean<T> implements InitializingBean,ApplicationContextAware {

    //Spring上下背景文
    private static transient ApplicationContext SPRING_CONTEXT;

    private transient ApplicationContext applicationContext;


    private ProviderConfig provider;

    /**
     * Spring加载完后会启动 这里
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //对基本的配置做初始化 没有的就加载相应的配置
        if (getProvider() == null) {
            //从Spring上下背景文中取出相应的Config  从handlerMapping中取
            Map<String, ProviderConfig> providerConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProviderConfig.class, false, false);
            if (providerConfigMap != null && providerConfigMap.size() > 0) {

            } else {

            }

        }




        }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public ProviderConfig getProvider() {
        return provider;
    }
}
