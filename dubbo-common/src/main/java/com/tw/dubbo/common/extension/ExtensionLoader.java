package com.tw.dubbo.common.extension;

import com.tw.dubbo.common.extension.ext1.SimpleExt;
import com.tw.dubbo.common.utils.ClassUtils;
import com.tw.dubbo.common.utils.Holder;
import com.tw.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * @author clay
 * @date 2018/11/29 14:51
 */
public class ExtensionLoader<T>  {


    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);


    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private final Class<?> type;

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    /**
     * 加载策略工厂类
     */
    private final ExtensionFactory objectFactory;
    private volatile Throwable createAdaptiveInstanceError;
    private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();
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


    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");


    /**
     * 缓存
     */
    private final Holder<Object> cachedAdaptiveInstance = new Holder<>();

    private volatile Class<?> cachedAdaptiveClass = null;


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
//            //为啥异常要这样玩
//            if (createAdaptiveInstanceError != null) {
//                throw new IllegalStateException("Failed to create adaptive instance: " +
//                        createAdaptiveInstanceError.toString(),
//                        createAdaptiveInstanceError);
//            }
//
//            //线程安全双重锁获取
//            synchronized (cachedAdaptiveInstance) {
//                instance = cachedAdaptiveInstance.get();
//                if (instance == null) {
//                    try {
//
//                    } catch (Throwable t) {
//                        createAdaptiveInstanceError = t;
//                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
//                    }
//                }
//            }
            instance = createAdaptiveExtension();
            cachedAdaptiveInstance.set(instance);
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

    private static <T> boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

    private Class<?> getExtensionClass(String name) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Extension name == null");
        }


        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type (" + type +
                    ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
        }

        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new IllegalStateException("No such extension \"" + name + "\" for " + type.getName() + "!");
        }
        return clazz;
    }




    public Class<?> getAdaptiveExtensionClass() {
        getExtensionClasses();
        if (cachedAdaptiveClass != null) {
            return cachedAdaptiveClass;
        }

        return createAdaptiveExtensionClass();
    }

    private Class<?> createAdaptiveExtensionClass() {

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
     *
     * */
    private Map<String,Class<?>> loadExtensionClasses() {

        Map<String, Class<?>> extensionClasses = new HashMap<>();
        //扫描策略下的配置目录的SPI文件
        for (LoadingStrategy strategy : strategies) {
            loadDirectory(extensionClasses, strategy.directory(), type.getName(), strategy.preferExtensionClassLoader(), strategy.excludedPackages());

        }

        return extensionClasses;

    }

    /**
     * 加载SPI注解的类到Map中
     * @param extensionClasses 加载容器
     * @param directory 目录
     * @param type 类名
     * @param extensionLoaderClassLoaderFirst
     * @param excludedPackages
     */
    private void loadDirectory(Map<String, Class<?>> extensionClasses, String directory, String type,  boolean extensionLoaderClassLoaderFirst, String... excludedPackages) {
        //Step1 获取类加载器
        //step2 获取资源的相对路径
        String fileName = directory + type;

        try {
            Enumeration<URL> urls = null;
            ClassLoader classLoader = findClassLoader();

            //获取类的相对路径
            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }

            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL resourceURL = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceURL, excludedPackages);
                }
            }

        } catch (Throwable t) {
            logger.error("Exception occurred when loading extension class (interface: " +
                    type + ", description file: " + fileName + ").", t);
        }


    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader,  java.net.URL resourceURL, String... excludedPackages) {
        //step1 转化成buffer流 读文件内容
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8));
            String line;
            //逐行读取
            while((line = reader.readLine()) != null) {
                //去除空格
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        String name = null;
                        int i = line.indexOf('=');
                        if (i > 0) {
                            name = line.substring(0, i).trim();
                            line = line.substring(i + 1).trim();
                        }
                        //这里加入排除函数
                        // Class.forName(line, true, classLoader) 就已经根据配置加载了
                        if (line.length() > 0) {
                            loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
                        }
                    } catch (Throwable t) {
                        IllegalStateException e = new IllegalStateException("Failed to load extension class (interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name) throws NoSuchMethodException {
        if (!type.isAssignableFrom(clazz)) {
            throw new IllegalStateException("Error occurred when loading extension class (interface: " +
                    type + ", class line: " + clazz.getName() + "), class "
                    + clazz.getName() + " is not subtype of interface.");
        }
        clazz.getConstructor();

        if (StringUtils.isNotEmpty(name)) {
            //放到缓存变量中
            cacheName(clazz, name);
            //放到ExtensionClass的Map中
            saveInExtensionClass(extensionClasses, clazz, name);

        }


    }
    /**
     * put clazz in extensionClasses
     */
    private void saveInExtensionClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name) {
        Class<?> c = extensionClasses.get(name);
        if (c == null) {
            extensionClasses.put(name, clazz);
        } else if (c != clazz) {
            String duplicateMsg = "Duplicate extension " + type.getName() + " name " + name + " on " + c.getName() + " and " + clazz.getName();
            logger.error(duplicateMsg);
            throw new IllegalStateException(duplicateMsg);
        }
    }

    /**
     * cache name
     */
    private void cacheName(Class<?> clazz, String name) {
        if (!cachedNames.containsKey(clazz)) {
            cachedNames.put(clazz, name);
        }
    }
    
    private ClassLoader findClassLoader() {
        return ClassUtils.getClassLoader(ExtensionLoader.class);

    }

    public SimpleExt getDefaultExtension() {
        return null;
    }

    public String getDefaultExtensionName() {
        return null;
    }
}
