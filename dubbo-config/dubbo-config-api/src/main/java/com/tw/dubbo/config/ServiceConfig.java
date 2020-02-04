package com.tw.dubbo.config;


import com.tw.dubbo.common.URLBuilder;
import com.tw.dubbo.common.bytecode.Wrapper;
import com.tw.dubbo.common.config.*;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.utils.*;
import com.tw.dubbo.config.util.ConfigValidationUtils;
import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Protocol;
import com.tw.dubbo.rpc.ProxyFactory;
import com.tw.dubbo.rpc.ServiceClassContain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static com.tw.dubbo.common.constants.CommonConstants.*;
import static com.tw.dubbo.common.utils.Constants.TIMESTAMP_KEY;
import static com.tw.dubbo.common.utils.NetUtils.isInvalidLocalHost;

/**
 *
 *
 * @author clay
 * @date 2018/11/27 14:25
 */
public class ServiceConfig<T> extends ServiceConfigBase<T>  {

    protected static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
    private static final Protocol protocol =
            ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    public ServiceConfig() {}


    /**
     * 发布接口集合
     */
    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();



    protected Boolean exported = false;

    protected ApplicationConfig application;

    private transient volatile boolean unexported = false;




    /**
     * service monitor
     */
    protected MonitorConfig monitor;
    /**
     * 包路径
     */
    private String path;

    private String host;


    public synchronized void export() {
        if (!shouldExport()) {
            return;
        }
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
        checkDefaultProvider();

        //step2 register 注册中心信息
        checkRegistry();

        //step3 校验协议RPC协议信息
        checkProtocol();


        //step4 刷新配置 如果发布方有重新设置环境参数
        this.refresh();


        //step5 接口提供方有指定接口与接口实现类
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
        checkInterfaceAndMethods(interfaceClass, getMethods());
        //校验接口实现类
        checkRef();
//        generic = Boolean.FALSE.toString();

        // TODO 目前是空方法
        checkStubAndLocal(interfaceClass);
        ConfigValidationUtils.checkMock(interfaceClass, this);


        ConfigValidationUtils.validateServiceConfig(this);

        // TODO 目前是空方法
        appendParameters();

    }

    public void appendParameters() {

    }

    public List<MethodConfig>  getMethods(){

        return new ArrayList<>();

    }
    private void checkInterfaceAndMethods(Class<?> interfaceClass, List<MethodConfig> methods){
        //类不能为空Class<?>
        Assert.notNull(interfaceClass, new IllegalStateException("interface not allow null!"));
        //不能为为接口类

        if (!interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        //TODO finish it
        if (CollectionUtils.isNotEmpty(methods)) {
        }


    }
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
     * 校验是否有注册中心信息 and then conversion it to {@link RegistryConfig}
     *
     */
    @Override
    public void checkRegistry() {
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



    protected synchronized void doExport() {
        //是否已经发布 防止多次发布
        if (unexported) {
            throw new IllegalStateException("Already unexported!");
        }

        if (exported) {
            return;
        }
        exported = true;

        doExportUrls();

    }

    private void doExportUrls() {
        //将接口信息放到 本地缓存 也就是map中
        // provider与service信息以后从容器类中存取
        List<URL> registryURLs = loadRegistries(true);

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
        //默认是dubbo协议
        if (name == null || name.length() == 0) {
            name = DUBBO;
        }

        //step1 关联信息解析放入dubbo中
        Map<String, String> map = new HashMap<>();
        map.put(SIDE_KEY, PROVIDER_SIDE);
        //放入dubbo版本号 时间戳 pid
        ServiceConfig.appendRuntimeParameters(map);

//        AbstractConfig.appendParameters(map, getMetrics());
        AbstractConfig.appendParameters(map, getApplication());
//        AbstractConfig.appendParameters(map, getModule());
        // remove 'default.' prefix for configs from ProviderConfig
        // appendParameters(map, provider, Constants.DEFAULT_KEY);
        AbstractConfig.appendParameters(map, provider);
        AbstractConfig.appendParameters(map, protocolConfig);
        AbstractConfig.appendParameters(map, this);


        //获取类的所有方法名
        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            logger.warn("没有找到相应的接口实现: " + interfaceClass.getName());
            map.put("methods", "*");
        } else {
            //把数组处理成一个字符串
            map.put("methods", StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        }
        String contextPath = null;

        //初始化属性到serviceMetadata中
//        serviceMetadata.getAttachments().putAll(map);

        //准备好数据可以开始请求 之前在干嘛啊 搞了那么多乱七八糟的没用的
        String host = this.findConfigedHosts(protocolConfig, registryURLs, map);
        Integer port = this.findConfigedPorts(protocolConfig, name, map);

        //这里构建的URL  前面构造好的数据
        URL url = new URL(name, host, port, (contextPath == null || contextPath.length() == 0 ? "" : contextPath + "/") + path, map);


        //本地暴露接口
        exportLocal(url);

    }

    private static void appendRuntimeParameters(Map<String, String> map) {

        map.put(DUBBO_VERSION_KEY, Version.getProtocolVersion());
        map.put(RELEASE_KEY, Version.getVersion());
        map.put(TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));

        if (ConfigUtils.getPid() > 0) {
                map.put(PID_KEY, String.valueOf(ConfigUtils.getPid()));
        }
    }

    protected Class getServiceClass(T ref) {
        return ref.getClass();
    }

    private void exportLocal(URL url) {

        //转化url为本地格式
        //mockprotocol2://192.168.10.1:7629/org.apache.dubbo.config.api.DemoService?anyhost=true&application=app&bind.ip=192.168.10.1&bind.port=7629&deprecated=false&dubbo=2.0.2&dynamic=true&export=true&generic=false&interface=org.apache.dubbo.config.api.DemoService&methods=getUsers,sayName,echo,getBox,throwDemoException&pid=8472&release=&side=provider&timestamp=1580612938735

        //injvm://127.0.0.1/org.apache.dubbo.config.api.DemoService?anyhost=true&application=app&bind.ip=192.168.10.1&bind.port=7629&deprecated=false&dubbo=2.0.2&dynamic=true&export=true&generic=false&interface=org.apache.dubbo.config.api.DemoService&methods=getUsers,sayName,echo,getBox,throwDemoException&pid=8472&release=&side=provider&timestamp=1580612938735
        //转换后

        URL local = URLBuilder.from(url)
                .setProtocol(LOCAL_PROTOCOL)
                .setHost(LOCALHOST_VALUE)
                .setPort(0)
                .build();


        //equalsIgnoreCase 忽略大小写
        if (!"injvm".equalsIgnoreCase(url.getProtocol())) {
            //将URL的协议与IP等设置成本地
            ServiceClassContain.getInstance().pushServiceClass(getServiceClass(ref));
            //本地发布的关键代码
//            Exporter<?> exporter = protocol.export(
//                    proxyFactory.getInvoker(ref, (Class) interfaceClass, local));
//            exporters.add(exporter);
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
                map.put(TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
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

    @Deprecated
    private static ProtocolConfig convertProviderToProtocol(ProviderConfig provider) {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(provider.getProtocol().getName());
        protocol.setHost(provider.getHost());
        protocol.setPort(provider.getPort());
        protocol.setThreads(provider.getThreads());
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
//        setInterface(interfaceClass == null ? null : interfaceClass.getName());
    }


    public String getPath() {
        return path;
    }




    @Override
    public Boolean getExport() {
        return export;
    }

    @Override
    public void setExport(Boolean export) {
        this.export = export;
    }

    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }



    @Override
    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }



    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }



    @Override
    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


}
