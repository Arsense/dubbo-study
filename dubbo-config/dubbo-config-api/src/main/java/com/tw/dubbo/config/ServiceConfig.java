package com.tw.dubbo.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tw.dubbo.common.util.Constants;
import com.tw.dubbo.common.util.UrlUtils;
import com.tw.dubbo.common.util.Version;
/**
 * @author tangwei
 * @date 2018/11/27 14:25
 */
public class ServiceConfig<T>  {
    private Boolean isDefault;
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

    // service monitor
    protected MonitorConfig monitor;
    //包路径
    private String path;



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
        List<URL> registryURLs = loadRegistries(true);

    }

    protected List<URL> loadRegistries(boolean provider) {
        //把各种配置放到map 然后拼接成URL
        List<URL> registryList = new ArrayList<URL>();
        if (registries == null || registries.isEmpty()) {
            return null;
        }
        for (RegistryConfig config : registries) {
            //zookeeper://127.0.0.1:2181
            String address = config.getAddress();
            if (address == null || address.length() == 0) {
                address = "0.0.0.0";
            }
            //文件配置权限优先级高
            String sysaddress = System.getProperty("dubbo.registry.address");
            if (sysaddress != null && sysaddress.length() > 0) {
                address = sysaddress;
            }
            if (address.length() > 0){
                Map<String, String> map = new HashMap<String, String>();
//                map.put("path", RegistryService.class.getName());
                //dubbo版本
                map.put("dubbo", Version.getProtocolVersion());
                //时间粗
                map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
                List<URL> urls = UrlUtils.parseURLs(address, map);
            }


        }

        return registryList;

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


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public List<ProtocolConfig> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterface() {
        return interfaceName;
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
