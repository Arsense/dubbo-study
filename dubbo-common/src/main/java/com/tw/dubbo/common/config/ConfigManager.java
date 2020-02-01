package com.tw.dubbo.common.config;

import com.tw.dubbo.common.context.FrameworkExt;
import com.tw.dubbo.common.context.LifecycleAdapter;
import com.tw.dubbo.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.tw.dubbo.common.config.AbstractConfig.getTagName;
import static com.tw.dubbo.common.constants.CommonConstants.DEFAULT_KEY;
import static com.tw.dubbo.common.util.ReflectUtils.getProperty;
import static com.tw.dubbo.common.util.StringUtils.isNotEmpty;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyMap;

/**
 * @author clay
 * @date 2020/1/29 22:56
 * LifecycleAdapter 生命周期适配器
 */
public class ConfigManager extends LifecycleAdapter implements FrameworkExt {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);


    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    public static final String NAME = "config";
    /**
     * config本地缓存
     */
    private final Map<String, Map<String, AbstractConfig>> configsCache = new HashMap<>();

    private ApplicationConfig application;

    public Optional<ApplicationConfig> getApplication() {
//        return ofNullable(getConfig(getTagName(ApplicationConfig.class)));
          return null;
    }


    public ApplicationConfig getApplicationOrElseThrow() {
        return getApplication().orElseThrow(() -> new IllegalStateException("There's no ApplicationConfig specified."));
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
    /**
     * Add the dubbo {@link AbstractConfig config}
     *
     * @param config the dubbo {@link AbstractConfig config}
     */
    public void addConfig(AbstractConfig config) {
        addConfig(config, false);
    }
    protected void addConfig(AbstractConfig config, boolean unique) {
        if (config == null) {
            return;
        }
        //这种回调的方式也不是很会
        write(() -> {
            //初始化缓存
            Map<String, AbstractConfig> configsMap = configsCache.computeIfAbsent(getTagName(config.getClass()), type -> new HashMap<>());
            //添加config到缓存中
            addIfAbsent(config, configsMap, unique);
        });
    }

    /**
     * 添加config 到map中
     * @param config
     * @param configsMap
     * @param unique
     */
    static void  addIfAbsent(AbstractConfig config,Map<String, AbstractConfig> configsMap, boolean unique){
        if (config == null || configsMap == null) {
            return;
        }
        //是否需要唯一性校验
        if (unique) {
            configsMap.values().forEach(c -> {
                checkDulicate(c, config);
            });
        }
        //根据id获取 存在则对比前后是否一致 不存在则放入map中
        //存在且相等 需要抛重复异常 这里重写了equals

        //里面有种高级的内省用法
        String key = getId(config);

        AbstractConfig existedConfig = configsMap.get(key);

        if (existedConfig != null && !config.equals(existedConfig)) {

            if (logger.isWarnEnabled()) {
                String type = config.getClass().getSimpleName();
                logger.warn(String.format("Duplicate %s found, there already has one default %s or more than two %ss have the same id, " +
                        "you can try to give each %s a different id : %s", type, type, type, type, config));
            } else {
                configsMap.put(key, config);
            }
        }
    }

    static String getId(AbstractConfig config) {
        String id = config.getId();
        return isNotEmpty(id)? id : isDefaultConfig(config)?
                config.getClass().getSimpleName() + "#" + DEFAULT_KEY: null;

    }

    /**
     * 用内省的方式获取javaBean信息调用getDeault
     * @param config
     * @return
     */
    static  boolean isDefaultConfig(AbstractConfig config) {
        Boolean isDefault = getProperty(config, "default");
        return isDefault == null || TRUE.equals(isDefault);
    }



    private static void checkDulicate(AbstractConfig oldOne, AbstractConfig newOne) {
        if (oldOne != null && !oldOne.equals(newOne)) {
            String configName = oldOne.getClass().getSimpleName();
            logger.warn("Duplicate Config found for " + configName + ", you should use only one unique " + configName + " for one application.");
        }

    }


    public void addProvider(ProviderConfig providerConfig) {
        addConfig(providerConfig);
    }

    private void write(Runnable runnable) {
        write(() -> {
            runnable.run();
        });
    }

    /**
     * Only allows one default ProviderConfig
     */
    public Optional<ProviderConfig> getDefaultProvider() {
        List<ProviderConfig> providerConfigs = getDefaultConfigs(getConfigsMap(getTagName(ProviderConfig.class)));

        if (CollectionUtils.isNotEmpty(providerConfigs)) {
            return Optional.of(providerConfigs.get(0));
        }
        return Optional.empty();
    }

    protected <C extends AbstractConfig> Map<String, C> getConfigsMap(String configType) {
        return (Map<String, C>) read(() -> configsCache.getOrDefault(configType, emptyMap()));
    }


    /**
     * 回调的方式 并且读加了读锁
     * @param callable
     * @param <V>
     * @return
     */
    private <V> V read(Callable<V> callable) {
        Lock readLock = lock.readLock();
        V value = null;
        try {
            readLock.lock();
            value = callable.call();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            readLock.unlock();
        }
        return value;
    }

    static <C extends AbstractConfig> List<C> getDefaultConfigs(Map<String, C> configsMap) {
        return configsMap.values()
                .stream()
                .filter(ConfigManager::isDefaultConfig)
                .collect(Collectors.toList());
    }
}
