package com.tw.dubbo.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author tangwei
 * @date 2018/11/27 14:53
 */
public class ServiceBean<T> implements InitializingBean,ApplicationContextAware {

    //Spring上下背景文
    private static transient ApplicationContext SPRING_CONTEXT;


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
