package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.ApplicationConfig;
import com.tw.dubbo.config.ProtocolConfig;
import com.tw.dubbo.config.ProviderConfig;
import com.tw.dubbo.config.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/11/27 14:53
 */
public class ServiceBean<T> implements InitializingBean,ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent>{

    //Spring上下背景文
    private static transient ApplicationContext SPRING_CONTEXT;

    private transient ApplicationContext applicationContext;
    //集群协议列表
    protected List<ProtocolConfig> protocols;
    //提供者
    private ProviderConfig provider;
    //是否发布接口
    protected Boolean export;

    protected Boolean exported;

    protected ApplicationConfig application;

    // registry centers
    protected List<RegistryConfig> registries;
    //接口类型
    protected Class<?> interfaceClass;
    //接口名
    private String interfaceName;



    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    /**
     * Spring 上下背景文设置 主要是版本的兼容问题
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

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


    /**
     * ContextRefreshedEvent Spring加载完后触发该事件
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        logger.info("The service ready on spring started. service: " + getInterface());
        //Spring对所配置文件的配置和Bean准备装载OK 继续到下一步
          export();
    }

    public synchronized void export() {
        //判断是否发布
        if (provider != null) {
            if (export == null) {
                export = provider.getExport();
            }
        }

        doExport();
    }

    protected synchronized void doExport() {
        //是否已经发布 防止多次发布
        if (exported) {
            return;
        }
        exported = true;
//        checkDefault();

        if (provider != null) {
            //
            if (application == null) {
                application = provider.getApplication();
            }
            if (registries == null) {
                registries = provider.getRegistries();
            }

            if (protocols == null) {
                protocols = provider.getProtocols();
            }
        }

        if (interfaceName == null || interfaceName.length() == 0) {
            throw new IllegalStateException("<dubbo:service interface=\"\" /> interface not allow null!");
        }
        try {
            //三参类加载器 JVM会执行该类的静态代码段 先加载类
            interfaceClass = Class.forName(interfaceName, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
//        检查接口的方法 不检查会怎样
//        checkInterfaceAndMethods(interfaceClass, methods);
//        checkRef();  检查ref 与我们的bean的interface匹配不
        doExportUrls();

    }

    private void doExportUrls() {
//        List<URL> registryURLs = loadRegistries(true);
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


    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
}
