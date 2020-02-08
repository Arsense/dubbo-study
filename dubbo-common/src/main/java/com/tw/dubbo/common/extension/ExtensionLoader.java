package com.tw.dubbo.common.extension;

import com.tw.dubbo.common.utils.Holder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author clay
 * @date 2018/11/29 14:51
 */
public class ExtensionLoader<T>  {

    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<String, Holder<Object>>();

    private final Class<?> type;

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    /**
     * 加载策略工厂类
     */
    private final ExtensionFactory objectFactory;
    private volatile Throwable createAdaptiveInstanceError;

    /**
     *  服务目录？
     */
    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    /**
     * dubbo配置SPI目录
     */
    private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";

    /**
     *
     */
    private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";

    /**
     * 这是lamda 代表初始化第一个元素
      */
    private static LoadingStrategy DUBBO_INTERNAL_STRATEGY =  () -> DUBBO_INTERNAL_DIRECTORY;
    private static LoadingStrategy DUBBO_STRATEGY = () -> DUBBO_DIRECTORY;
    private static LoadingStrategy SERVICES_STRATEGY = () -> SERVICES_DIRECTORY;

    private static LoadingStrategy[] strategies = new LoadingStrategy[] { DUBBO_INTERNAL_STRATEGY, DUBBO_STRATEGY, SERVICES_STRATEGY };


    /**
     * 缓存
     */
    private final Holder<Object> cachedAdaptiveInstance = new Holder<>();

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

    public ExtensionLoader(Class<T> type) {
        this.type = type;
        //主要是变现初始化ExtensionFactory
        objectFactory = (type == ExtensionFactory.class ? null :
                ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
    }

    /**
     * 根据类型获取SPI注解上对应的类
     * 实际上是初始化的时候放入 一个concurrentHashMap
     * TODO question 什么时候初始化扫描注解存入 存入的 key-value规则是？
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        //非空 是接口 含@SPI注解
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }

        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        //关键的初始化操作在 new ExtensionLoader初始化构造方法里
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;

    }

    /**
     * 核心方法 加载接口对应的类
     * @return
     */
    public T getAdaptiveExtension() {
        Object instance = cachedAdaptiveInstance.get();
        if (instance == null) {
            //为啥异常要这样玩
            if (createAdaptiveInstanceError != null) {
                throw new IllegalStateException("Failed to create adaptive instance: " +
                        createAdaptiveInstanceError.toString(),
                        createAdaptiveInstanceError);
            }

            //线程安全双重锁获取
            synchronized (cachedAdaptiveInstance) {
                instance = cachedAdaptiveInstance.get();
                if (instance == null) {
                    try {
                        instance = createAdaptiveExtension();
                        cachedAdaptiveInstance.set(instance);
                    } catch (Throwable t) {
                        createAdaptiveInstanceError = t;
                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
                    }
                }
            }

        }

        return null;
    }

    /**
     * 获取接口适配的类
     * @return
     */
    private Object createAdaptiveExtension() {
        try {
            return injectExtension((T) getAdaptiveExtensionClass().newInstance());
        } catch (Exception e) {
            throw new IllegalStateException("Can't create adaptive extension " + type + ", cause: " + e.getMessage(), e);
        }
    }

    private Object injectExtension(T t) {
        //TODO
        return null;
    }




    public T getExtension(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Extension name == null");
        }

        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            //putIfAbsent类似于Put 不会覆盖数据
            cachedInstances.putIfAbsent(name, new Holder<Object>());
            holder = cachedInstances.get(name);
        }
        Object instance = holder.get();
        return (T) instance;
    }


    public boolean hasExtension(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Extension name == null");
        }
        try {
            this.getExtensionClass(name);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }


    private Class<?> getExtensionClass(String name) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Extension name == null");
        }
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new IllegalStateException("No such extension \"" + name + "\" for " + type.getName() + "!");
        }
        return clazz;
    }




    public Class<?> getAdaptiveExtensionClass() {
        getExtensionClasses();


        return null;
    }


    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = loadExtensionClasses();
        cachedClasses.set(classes);
        return classes;
    }


    /**
     *
     *
     * TODO synchronized in getExtensionClasses
     * */
    private Map<String,Class<?>> loadExtensionClasses() {

        Map<String, Class<?>> extensionClasses = new HashMap<>();
        //扫描策略下的配置目录的SPI文件
        for (LoadingStrategy strategy : strategies) {


        }
        return null;
    }
}
