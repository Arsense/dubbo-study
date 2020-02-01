package com.tw.dubbo.config.util;

import com.tw.dubbo.common.config.*;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.utils.CollectionUtils;
import com.tw.dubbo.common.utils.ConfigUtils;
import com.tw.dubbo.common.utils.StringUtils;
import com.tw.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tw.dubbo.common.constants.CommonConstants.*;

/**
 * config校验工具类
 * @author clay
 * @date 2020/1/22 21:53
 */
public class ConfigValidationUtils {

    /**
     * 属性配置最长限制
     */
    private static final int MAX_LENGTH = 200;
    /**
     * name的正则校验
     */
    private static final Pattern PATTERN_NAME = Pattern.compile("[\\-._0-9a-zA-Z]+");

    /**
     * key的正则校验
     */
    private static final Pattern PATTERN_KEY = Pattern.compile("[*,\\-._0-9a-zA-Z]+");

    /**
     * 扩展属性格式校验 ,号分隔 所以运行,
     */
    private static final Pattern PATTERN_MULTI_NAME = Pattern.compile("[,\\-._0-9a-zA-Z]+");

    /**
     * The rule qualification for <b>method names</b>
     */
    private static final Pattern PATTERN_METHOD_NAME = Pattern.compile("[a-zA-Z][0-9a-zA-Z]*");

    /**
     * 路径正则匹配
     */
    private static final Pattern PATTERN_PATH = Pattern.compile("[/\\-$._0-9a-zA-Z]+");

    /**
     * The pattern matches a value who has a symbol
     */
    private static final Pattern PATTERN_NAME_HAS_SYMBOL = Pattern.compile("[:*,\\s/\\-._0-9a-zA-Z]+");


    private static final Logger logger = LoggerFactory.getLogger(ConfigValidationUtils.class);
    public static void checkExtension(Class<?> type, String property, String value) {
        checkName(property, value);
        if (StringUtils.isNotEmpty(value)
                && !ExtensionLoader.getExtensionLoader(type).hasExtension(value)) {
            throw new IllegalStateException("No such extension " + value + " for " + property + "/" + type.getName());
        }
    }




    /**
     * 检查是否有扩展名（属性）为value（需要特殊处理）
     * @param type
     * @param property
     * @param value
     */
    public static void checkMultiExtension(Class<?> type, String property, String value) {
        checkMultiName(property, value);
        if (StringUtils.isNotEmpty(value)) {
            //分隔成数组
            String[] values = value.split("\\s*[,]+\\s*");
            for (String v : values) {
                //去除前面 "-"
                if (v.startsWith(REMOVE_VALUE_PREFIX)) {
                    v = v.substring(1);
                }
                //默认 default 不处理
                if (DEFAULT_KEY.equals(v)) {
                    continue;
                }
                if (!ExtensionLoader.getExtensionLoader(type).hasExtension(v)) {
                    throw new IllegalStateException("No such extension " + v + " for " + property + "/" + type.getName());
                }
            }

        }


    }

    public static void checkMultiName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_MULTI_NAME);
    }



        /**
         * 基础配置校验 1不能为空 2不能超过限定长度
         * @param property
         * @param value
         * @param maxlength
         * @param pattern
         */
    public static void checkProperty(String property, String value, int maxlength, Pattern pattern) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        if (value.length() > maxlength) {
            throw new IllegalStateException("Invalid " + property + "=\"" + value + "\" is longer than " + maxlength);
        }
        //正则匹配校验非法字符
        if (pattern != null) {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid " + property + "=\"" + value + "\" contains illegal " +
                        "character, only digit, letter, '-', '_' or '.' is legal.");
            }
        }
    }




    /**
     * 校验service属性
     * 前面校验必填字段  此处校验必填字段是否有非法字符配置传入
     *
     * @param config
     */
    public static  void validateServiceConfig(ServiceConfig config) {
        //版本号 组别 token 路径中是否有非法字符 用的正则匹配
        checkKey(VERSION_KEY, config.getVersion());
        checkKey(GROUP_KEY, config.getGroup());
        checkName(TOKEN_KEY, config.getToken());
        checkPathName(PATH_KEY, config.getPath());

//        checkMultiExtension(ExporterListener.class, "listener", config.getListener());

        //校验接口config字段信息
        validateAbstractInterfaceConfig(config);

        //校验注册信息
        List<RegistryConfig> registries = config.getRegistries();
        if (registries != null) {
            for (RegistryConfig registry : registries) {
                validateRegistryConfig(registry);
            }
        }


        List<ProtocolConfig> protocols = config.getProtocols();
        if (protocols != null) {
            for (ProtocolConfig protocol : protocols) {
                validateProtocolConfig(protocol);
            }
        }

        ProviderConfig providerConfig = config.getProvider();
        if (providerConfig != null) {
            validateProviderConfig(providerConfig);
        }

    }

    private static void validateProviderConfig(ProviderConfig config) {
//        checkPathName(CONTEXTPATH_KEY, config.getContextpath());
//        checkExtension(ThreadPool.class, THREADPOOL_KEY, config.getThreadpool());
//        checkMultiExtension(TelnetHandler.class, TELNET, config.getTelnet());
//        checkMultiExtension(StatusChecker.class, STATUS_KEY, config.getStatus());
//        checkExtension(Transporter.class, TRANSPORTER_KEY, config.getTransporter());
//        checkExtension(Exchanger.class, EXCHANGER_KEY, config.getExchanger());

    }

    private static void validateProtocolConfig(ProtocolConfig config) {
        if (config != null) {
            String name = config.getName();
            checkName("name", name);
            checkName(HOST_KEY, config.getHost());
            checkPathName("contextpath", config.getContextpath());

            //dubbo协议需要 单独校验的参数
            if (DUBBO_PROTOCOL.equals(name)) {
//                checkMultiExtension(Codec.class, CODEC_KEY, config.getCodec());
//                checkMultiExtension(Serialization.class, SERIALIZATION_KEY, config.getSerialization());
//                checkMultiExtension(Transporter.class, SERVER_KEY, config.getServer());
//                checkMultiExtension(Transporter.class, CLIENT_KEY, config.getClient());
            }

//            checkMultiExtension(TelnetHandler.class, TELNET, config.getTelnet());
//            checkMultiExtension(StatusChecker.class, "status", config.getStatus());
//            checkExtension(Transporter.class, TRANSPORTER_KEY, config.getTransporter());
//            checkExtension(Exchanger.class, EXCHANGER_KEY, config.getExchanger());
//            checkExtension(Dispatcher.class, DISPATCHER_KEY, config.getDispatcher());
//            checkExtension(Dispatcher.class, "dispather", config.getDispather());
//            checkExtension(ThreadPool.class, THREADPOOL_KEY, config.getThreadpool());
        }


    }

    private static void validateRegistryConfig(RegistryConfig config) {
        checkName(PROTOCOL_KEY, config.getProtocol());
        checkName(USERNAME_KEY, config.getUsername());
        checkLength(PASSWORD_KEY, config.getPassword());
        checkPathLength(FILE_KEY, config.getFile());
//        checkName(TRANSPORTER_KEY, config.getTransporter());
        checkName(SERVER_KEY, config.getServer());
        checkName(CLIENT_KEY, config.getClient());
        checkParameterName(config.getParameters());
    }


    private static void validateAbstractInterfaceConfig(ServiceConfig config) {

//        checkName(LOCAL_KEY, config.getLocal());
//        checkName("stub", config.getStub());
//        checkMultiName("owner", config.getOwner());

//        checkExtension(ProxyFactory.class, PROXY_KEY, config.getProxy());
//        checkExtension(Cluster.class, CLUSTER_KEY, config.getCluster());
//        checkMultiExtension(Filter.class, FILE_KEY, config.getFilter());
//        checkMultiExtension(InvokerListener.class, LISTENER_KEY, config.getListener());
//        checkNameHasSymbol(LAYER_KEY, config.getLayer());

        List<MethodConfig> methods = config.getMethods();
        if (CollectionUtils.isNotEmpty(methods)) {
            methods.forEach(ConfigValidationUtils::validateMethodConfig);
        }


    }

    public static void validateMethodConfig(MethodConfig config) {

//        checkExtension(LoadBalance.class, LOADBALANCE_KEY, config.getLoadbalance());
        checkParameterName(config.getParameters());
        checkMethodName("name", config.getName());

        String mock = config.getMock();
        if (StringUtils.isNotEmpty(mock)) {
            if (mock.startsWith(RETURN_PREFIX) || mock.startsWith(THROW_PREFIX + " ")) {
                checkLength(MOCK_KEY, mock);
            } else if (mock.startsWith(FAIL_PREFIX) || mock.startsWith(FORCE_PREFIX)) {
                checkNameHasSymbol(MOCK_KEY, mock);
            } else {
                checkName(MOCK_KEY, mock);
            }
        }

    }

    public static void checkMethodName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_METHOD_NAME);
    }


     public static void checkKey(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_KEY);
    }



    public static void checkLength(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, null);
    }


    public static void checkPathLength(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, null);

    }

    public static void checkNameHasSymbol(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_NAME_HAS_SYMBOL);
    }


    public static void checkParameterName(Map<String, String> parameters) {
        if (CollectionUtils.isEmptyMap(parameters)) {
            return;
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            checkNameHasSymbol(entry.getKey(), entry.getValue());
        }
    }


    public static void checkMock(Class<?> interfaceClass, AbstractInterfaceConfig config) {
        String mock = config.getMock();
        if (ConfigUtils.isEmpty(mock)) {
            return;
        }

    }

    private static void checkName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_NAME);
    }


    public static void checkPathName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_PATH);
    }



}
