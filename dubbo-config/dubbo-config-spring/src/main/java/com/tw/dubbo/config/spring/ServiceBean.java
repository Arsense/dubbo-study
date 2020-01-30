package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.*;
import com.tw.dubbo.config.ServiceConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author clay
 * @date 2018/11/27 14:53
 */
public class ServiceBean<T>  extends ServiceConfig<T> implements InitializingBean,ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent>{

    //Spring上下背景文
    private static transient ApplicationContext SPRING_CONTEXT;

    private transient ApplicationContext applicationContext;

    private  String beanName;

    public ServiceBean() {
        super();
//        this.service = null;
    }

    /**
     * Spring 上下背景文设置 主要是版本的兼容问题
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * Spring加载完后会启动 这里
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //对基本的配置做初始化 没有的就加载相应的配置
        //逻辑 1 判断是否有提供者 有再找协议类型
        if (getProvider() == null) {
            //从Spring上下背景文中取出相应的Config  从handlerMapping中取
            Map<String, ProviderConfig> providerConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProviderConfig.class, false, false);
            if (providerConfigMap != null && providerConfigMap.size() > 0) {
                //分布式服务器用啥类型 zk还是什么的
                Map<String, ProtocolConfig> protocolConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
                //有提供者 没有配置协议 走默认协议
                if ((protocolConfigMap == null || protocolConfigMap.size() == 0)
                        && providerConfigMap.size() > 1) { // backward compatibility
                    List<ProviderConfig> providerConfigs = new ArrayList<ProviderConfig>();
                    for (ProviderConfig config : providerConfigMap.values()) {
                        //添加默认的
                        if (config.isDefault() != null && config.isDefault()) {
                            providerConfigs.add(config);
                        }
                    }
                    if (!providerConfigs.isEmpty()) {
                        setProviders(providerConfigs);
                    }
                } else {
                    ProviderConfig providerConfig = null;
                    //从Map中取出非default函数
                    for (ProviderConfig config : providerConfigMap.values()) {
                        if (config.isDefault() == null || config.isDefault()) {
                            if (providerConfig != null) {
                                throw new IllegalStateException("Duplicate provider configs: " + providerConfig + " and " + config);
                            }
                            providerConfig = config;
                        }
                        if (providerConfig != null) {
                            //处理到Protrol的配置里了
                            setProvider(providerConfig);
                        }
                    }
                }

            } else {

            }

        }

        //配置基本应用
        if (getApplication() == null) {
            Map<String, ApplicationConfig> applicationConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ApplicationConfig.class, false, false);
            if (applicationConfigMap != null && applicationConfigMap.size() > 0) {
                for (ApplicationConfig config : applicationConfigMap.values()) {
                    ApplicationConfig applicationConfig = null;
                    if (config.isDefault() == null || config.isDefault()) {
                        applicationConfig = config;
                    }
                    if (applicationConfig != null) {
                        setApplication(applicationConfig);
                    }
                }
            }
        }

//        //配置module
//        if (getModule() == null) {
//
//        }
        //检查注册中心
        if ((getRegistries() == null || getRegistries().isEmpty())){
            Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
            if (registryConfigMap != null && registryConfigMap.size() > 0) {
                List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                for (RegistryConfig config : registryConfigMap.values()) {
                    if (config.isDefault() == null || config.isDefault()) {
                        registryConfigs.add(config);
                    }
                }
                if (!registryConfigs.isEmpty()) {
                    setRegistries(registryConfigs);
                }
            }
        }


        if (getMonitor() == null){
            Map<String, MonitorConfig> monitorConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, MonitorConfig.class, false, false);
            if (monitorConfigMap != null && monitorConfigMap.size() > 0) {
                MonitorConfig monitorConfig = null;
                for (MonitorConfig config : monitorConfigMap.values()) {
                    if (config.isDefault() == null || config.isDefault()) {
                        if (monitorConfig != null) {
                            throw new IllegalStateException("Duplicate monitor configs: " + monitorConfig + " and " + config);
                        }
                        monitorConfig = config;
                    }
                }
                if (monitorConfig != null) {
                    setMonitor(monitorConfig);
                }
            }
        }

        if ((getProtocols() == null || getProtocols().isEmpty())
                && (getProvider() == null || getProvider().getProtocols() == null || getProvider().getProtocols().isEmpty())) {
            Map<String, ProtocolConfig> protocolConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
            if (protocolConfigMap != null && protocolConfigMap.size() > 0) {
                List<ProtocolConfig> protocolConfigs = new ArrayList<ProtocolConfig>();
                for (ProtocolConfig config : protocolConfigMap.values()) {
                    if (config.isDefault() == null || config.isDefault()) {
                        protocolConfigs.add(config);
                    }
                }
                if (!protocolConfigs.isEmpty()) {
                    super.setProtocols(protocolConfigs);
                }
            }
        }
        //设置包路径
//        if (getPath() == null || getPath().length() == 0) {
//            if (beanName != null && beanName.length() > 0
//                    && getInterface() != null && getInterface().length() > 0
//                    && beanName.startsWith(getInterface())) {
//                setPath(beanName);
//            }
//        }

//        export();

    }


    /**
     * ContextRefreshedEvent Spring加载完后触发该事件
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Service启动完成. service: " + getInterface());
        //Spring对所配置文件的配置和Bean准备装载OK 继续到下一步
        export();
    }


    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


    public boolean getInterface() {
        return true;
    }
}
