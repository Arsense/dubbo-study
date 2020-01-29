package com.tw.dubbo.common.config;

import com.tw.dubbo.common.bytecode.Wrapper;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.util.*;
import com.tw.dubbo.common.util.Constants;
import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Protocol;
import com.tw.dubbo.rpc.ProxyFactory;
import com.tw.dubbo.rpc.ServiceClassContain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static com.tw.dubbo.common.util.NetUtils.isInvalidLocalHost;

/**
 *
 *
 * @author clay
 * @date 2018/11/27 14:25
 */
public class ServiceConfig<T> extends AbstractConfig {

    protected static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
    private static final Protocol protocol =
            ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory proxyFactory =
            ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    public ServiceConfig() {}

    // 接口实现
    private T ref;
    //发布接口集合
    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    //集群协议列表
    protected List<ProtocolConfig> protocols;
    //提供者
    private ProviderConfig provider;
    //是否发布接口
    protected Boolean export;

    protected Boolean exported = false;

    protected ApplicationConfig application;

    private transient volatile boolean unexported = false;

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

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public synchronized void export() {

        //先校验 服务端接口暴露必备信息校验
        checkAndUpdateSubConfigs();

        //判断是否发布
        if (provider != null) {
            if (export == null) {
                export = provider.getExport();
            }
        }
        doExport();
    }

    private void checkAndUpdateSubConfigs() {
        //step1 provider 是否存在不存在则创建
        //step2 register 注册中心信息
        //step3 校验协议RPC协议信息
        //step4 刷新配置 如果发布方有重新设置环境参数
        //step5 接口提供方有指定接口与接口实现类
        checkDefaultProvider();
        checkRegistry();
        checkProtocol();
        this.refresh();

        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("<dubbo:service interface=\"\" /> interface not allow null!");
        }

        try {
            interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                    .getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        //校验接口方法不为空
//        checkInterfaceAndMethods(interfaceClass, getMethods());
        //校验接口实现类
        checkRef();
//        generic = Boolean.FALSE.toString();

    }


    public void getMethods(){

    }
//    private void checkInterfaceAndMethods(Class<?> interfaceClass, List<MethodConfig> methods){
//
//
//    }
    /**
     * 接口实现类校验
     */
    private void checkRef() {
        if (ref == null) {
            throw new IllegalStateException("ref not allow null!");
        }
        if (!interfaceClass.isInstance(ref)) {
            throw new IllegalStateException("The class "
                    + ref.getClass().getName() + " unimplemented interface "
                    + interfaceClass + "!");
        }
    }

    /**
     * 校验基本的协议信息
     */
    private void checkProtocol() {
        if (CollectionUtils.isEmpty(protocols) && provider != null) {
            setProtocols(provider.getProtocols());
        }
//        convertProtocolIdsToProtocols();

    }

    /**
     * 校验是否有注册中心信息
     */
    private void checkRegistry() {
        convertRegistryIdsToRegistries();
        for (RegistryConfig registryConfig : registries) {
            if (!registryConfig.isValid()) {
                throw new IllegalStateException("No registry config found or it's not a valid config! " +
                        "The registry config is: " + registryConfig);
            }
        }
    }

    private void convertRegistryIdsToRegistries() {
    }

    /**
     * 是否需要创建默认的provider
     */
    private void checkDefaultProvider() {

    }


    protected synchronized void doExport() {
        //是否已经发布 防止多次发布
        if (unexported) {
            throw new IllegalStateException("Already unexported!");
        }

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
        //TODO 检查基础配置是否OK
//        检查接口的方法 不检查会怎样
//        checkInterfaceAndMethods(interfaceClass, methods);
//        checkRef();  检查ref 与我们的bean的interface匹配不
        doExportUrls();

    }

    private void doExportUrls() {
        List<URL> registryURLs = loadRegistries(true);
        //因为可能有多个接口 所以是protocols
        //后面只要这里check了 就不用 检查了 临时的
        if (protocols == null) {
            throw new RuntimeException("protocols 未初始化");
        }
        for (ProtocolConfig protocolConfig : protocols) {
            doExportUrlsForProtocol(protocolConfig, registryURLs);
        }
    }

    /**
     * 开始发布
     * @param protocolConfig
     * @param registryURLs
     */
    private void doExportUrlsForProtocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {

        String name = protocolConfig.getName();
        if (name == null || name.length() == 0) {
            name = "dubbo";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("side", "provider");
        map.put("dubbo", Version.getProtocolVersion());
        map.put(com.tw.dubbo.common.util.Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));

        //这里已经获取到了DemoService的 sayHello函数
        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            logger.warn("没有找到相应的接口实现: " + interfaceClass.getName());
            map.put("methods", "*");
        } else {
            //把数组处理成一个字符串
            map.put("methods", StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        }
        String contextPath = null;

        //准备好数据可以开始请求 之前在干嘛啊 搞了那么多乱七八糟的没用的
        String host = this.findConfigedHosts(protocolConfig, registryURLs, map);
        Integer port = this.findConfigedPorts(protocolConfig, name, map);
        //这里构建的URL  前面构造好的数据
        URL url = new URL(name, host, port, (contextPath == null || contextPath.length() == 0 ? "" : contextPath + "/") + path, map);
        exportLocal(url);
        String scope = null;
//        if (!"local".equalsIgnoreCase(scope)){
//            if (registryURLs != null && !registryURLs.isEmpty()) {
//
//            }
//
//        }



    }

    protected Class getServiceClass(T ref) {
        return ref.getClass();
    }

    private void exportLocal(URL url) {
        //equalsIgnoreCase 忽略大小写
        if (!"injvm".equalsIgnoreCase(url.getProtocol())) {
            //将URL的协议与IP等设置成本地
            URL local = URL.valueOfUrl(url.toFullString())
                    .setProtocol("injvm")
                    .setHost("127.0.0.1")
                    .setPort(0);
            ServiceClassContain.getInstance().pushServiceClass(getServiceClass(ref));
            //本地发布的关键代码
            Exporter<?> exporter = protocol.export(
                    proxyFactory.getInvoker(ref, (Class) interfaceClass, local));
            exporters.add(exporter);
            logger.info("发布DUBBO接口 " + interfaceClass.getName() + " 道本地注册中心");
        }
    }
        protected List<URL> loadRegistries(boolean provider) {
        //把各种配置放到map 生成相应的URL类
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
                //拼接协议
                if (!map.containsKey("protocol")) {
                    map.put("protocol", "dubbo");
                }

                List<URL> urls = UrlUtils.parseURLs(address, map);
                for (URL url : urls) {
//                    url = url.addParameter(Constants.REGISTRY_KEY, url.getProtocol());
//                    url = url.setProtocol(Constants.REGISTRY_PROTOCOL);
//                    if ((provider && url.getParameter(Constants.REGISTER_KEY, true)){
                        registryList.add(url);
//                    }
                }
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


    private Integer findConfigedPorts(ProtocolConfig protocolConfig, String name, Map<String, String> map) {
        Integer portToBind = null;
        //Protocol 配置的端口 用类加载器之类的
        final int defaultPort = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(name).getDefaultPort();
        if (portToBind == null || portToBind == 0) {
            portToBind = defaultPort;
        }
        map.put("bind.port", String.valueOf(portToBind));

        return portToBind;
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
     * *为服务提供商注册和绑定IP地址，可以单独配置。
           *配置优先级：环境变量 - > java系统属性 - >配置文件中的主机属性 - >
           * / etc / hosts  - >默认网络地址 - >第一个可用的网络地址
     * @param protocolConfig
     * @param registryURLs
     * @param map
     * @return
     */
    private String findConfigedHosts(ProtocolConfig protocolConfig, List<URL> registryURLs, Map<String, String> map) {
        boolean anyhost = false;

        String hostToBind = null;
        hostToBind = protocolConfig.getHost();
        //protocol找不到就去provider找
        if (provider != null && (hostToBind == null || hostToBind.length() == 0)) {
            hostToBind = provider.getHost();
        }
        if (isInvalidLocalHost(hostToBind)) {
            anyhost = true;
            try {
                hostToBind = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.warn(e.getMessage(), e);
            }
            if (isInvalidLocalHost(hostToBind)) {

            }
        }
        map.put("bind.ip", hostToBind);
        String hostToRegistry = hostToBind;
        map.put("anyhost", String.valueOf(anyhost));
        return hostToRegistry;
    }


    /**
     * 设置注册信息
     * @param registry
     */
    public void setRegistry(RegistryConfig registry) {
        List<RegistryConfig> registries = new ArrayList<RegistryConfig>(1);
        registries.add(registry);
        setRegistries(registries);

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

    public void setInterface(Class<?> interfaceClass) {
        if (interfaceClass != null && !interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? null : interfaceClass.getName());
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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


    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
