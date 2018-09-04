package com.remote.test.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author tangwei
 * @date 2018/9/4 12:36
 */
public class RevokerFactoryBean  implements FactoryBean, InitializingBean {


    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
