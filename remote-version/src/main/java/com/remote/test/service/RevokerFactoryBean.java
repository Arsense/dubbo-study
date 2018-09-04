package com.remote.test.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author tangwei
 * @date 2018/9/4 12:36
 */
public class RevokerFactoryBean  implements FactoryBean, InitializingBean {


    private String targetInterface;

    private String appKey;



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




    public String getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(String targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
