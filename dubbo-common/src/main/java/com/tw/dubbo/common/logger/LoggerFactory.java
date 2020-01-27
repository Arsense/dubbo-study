package com.tw.dubbo.common.logger;

import com.tw.dubbo.common.extension.ExtensionLoader;

/**
 *
 * logger 工厂
 * @author clay
 * @date 2020/1/24 18:17
 */
public class LoggerFactory {
    /**
     * 日志打印类容器
     */
//    private static final ConcurrentMap<String, FailsafeLogger> LOGGERS = new ConcurrentHashMap<>();

    /**
     * 静态初始化
     */
    static {


    }

    /**
     * 构造方法
     */
    private LoggerFactory() {
    }

    public static void setLoggerAdapter(String loggerAdapter) {
        if (loggerAdapter != null && loggerAdapter.length() > 0) {
            setLoggerAdapter(ExtensionLoader.getExtensionLoader(LoggerAdapter.class).getExtension(loggerAdapter));
        }
    }

    public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
        if (loggerAdapter != null) {
//            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
//            logger.info("using logger: " + loggerAdapter.getClass().getName());
//            LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
//            for (Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet()) {
//                entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
//            }
        }

    }
}
