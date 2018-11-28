package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ApplicationConfig;
import com.tw.dubbo.config.ProtocolConfig;
import com.tw.dubbo.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/11/27 14:53
 */
public class ServiceBean<T> implements InitializingBean,ApplicationContextAware {

    //Spring上下背景文
    private static transient ApplicationContext SPRING_CONTEXT;

    private transient ApplicationContext applicationContext;

    protected List<ProtocolConfig> protocols;

    private ProviderConfig provider;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    protected ApplicationConfig application;


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

        }

//        //配置module
//        if (getModule() == null) {
//
//        }
        //检查注册中心
//        if (getRegistries() == null) {
//
//        }
//        if (getMonitor() == null) {
//
//        }
//        //配置协议
//        if (getProtocols() == null ){
//
//        }
//
//        if (!isDelay()) {
//            export();
//        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }


    public List<ProviderConfig> getProviders() {
        return convertProtocolToProvider(protocols);
    }

    /**
     * @deprecated Replace to setProtocols()
     */
    // @Deprecated不允许改方法名

    public void setProviders(List<ProviderConfig> providers) {
        this.protocols = convertProviderToProtocol(providers);
    }

    public ProviderConfig getProvider() {
        return provider;
    }
    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    /**
     *  Protocol 转换成Provider
     * @param protocols
     * @return
     */
    private static List<ProviderConfig> convertProtocolToProvider(List<ProtocolConfig> protocols) {
        if (protocols == null || protocols.isEmpty()) {
            return null;
        }
        //细节 省空间protocols.size()
        List<ProviderConfig> providers = new ArrayList<ProviderConfig>(protocols.size());
        for (ProtocolConfig provider : protocols) {
            providers.add(convertProtocolToProvider(provider));
        }
        return providers;
    }

    /**
     * Provider 中配置到Protocol里面
     * @param providers
     * @return
     */
    private static List<ProtocolConfig> convertProviderToProtocol(List<ProviderConfig> providers) {
        if (providers == null || providers.isEmpty()) {
            return null;
        }
        List<ProtocolConfig> protocols = new ArrayList<ProtocolConfig>(providers.size());
        for (ProviderConfig provider : providers) {
            protocols.add(convertProviderToProtocol(provider));
        }
        return protocols;
    }


    private static ProtocolConfig convertProviderToProtocol(ProviderConfig provider) {
        ProtocolConfig protocol = new ProtocolConfig();
        return protocol;
    }


    private static ProviderConfig convertProtocolToProvider(ProtocolConfig protocol) {
        ProviderConfig provider = new ProviderConfig();
        return provider;
    }
}
