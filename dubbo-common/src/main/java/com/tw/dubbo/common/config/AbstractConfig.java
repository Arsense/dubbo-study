package com.tw.dubbo.common.config;

import com.tw.dubbo.common.config.annotation.Parameter;
import com.tw.dubbo.common.constants.CommonConstants;
import com.tw.dubbo.common.utils.MethodUtils;
import com.tw.dubbo.common.utils.StringUtils;
import com.tw.dubbo.common.utils.URL;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *     属性太多挤不下 都放都公共的抽象类里 节省代码量
 * </pre>
 *
 *
 * @author clay
 * @date 2018/12/5 14:59
 */
public abstract class AbstractConfig implements Serializable {

    protected String id;
    /**
     *
     */
    protected Boolean isDefault;

    protected String prefix;


    @Parameter(excluded = true)
    public String getPrefix() {
        return StringUtils.isNotEmpty(prefix) ? prefix : (CommonConstants.DUBBO + "." + getTagName(this.getClass()));
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    /**
     * 更新配置
     */
    public void refresh() {

    }


    /**
     * 重写AbstractConfig比较方法
     * 除了了类名相等之外
     * getter方法下的@Paramater注解也要完全一样才可
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj.getClass().getName().equals(this.getClass().getName()))) {
            return false;
        }

        Method[] methods = this.getClass().getMethods();
        //遍历方法 对比get方法上的注解属性
        for (Method method1 : methods) {

            if (MethodUtils.isGetter(method1)) {
                Parameter parameter = method1.getAnnotation(Parameter.class);
                if (parameter != null && parameter.excluded()) {
                    continue;
                }
                try {
                    //get
                    Method method2 = obj.getClass().getMethod(method1.getName(), method1.getParameterTypes());
                    Object value1 = method1.invoke(this, new Object[]{});
                    Object value2 = method2.invoke(obj, new Object[]{});
                    if (!Objects.equals(value1, value2)) {
                        return false;
                    }
                } catch (Exception e) {
                    return true;
                }
            }
        }

        return true;
    }

    /**
     * getter方法下的@Paramater注解也要完全一样才可
     * 这里为了减小重复率 还做了散列
     * @return
     */
    @Override
    public int hashCode() {

        int hashCode = 1;

        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (MethodUtils.isGetter(method)) {
                Parameter parameter = method.getAnnotation(Parameter.class);
                if (parameter != null && parameter.excluded()) {
                    continue;
                }
                try {
                    Object value = method.invoke(this, new Object[]{});
                    //这里hash 散列 X31 相当于左移4位 加相当于或吧
                    hashCode = 31 * hashCode + value.hashCode();
                } catch (Exception ignored) {
                    //ignored
                }
            }
        }

        if (hashCode == 0) {
            hashCode = 1;
        }

        return hashCode;
    }

    /**
     * 不含前缀的方法
     *
     * @param parameters
     * @param config
     */
    public static void appendParameters(Map<String, String> parameters, Object config) {
        appendParameters(parameters, config, null);
    }
    /**
     * The suffix container
     */
    private static final String[] SUFFIXES = new String[]{"Config", "Bean", "ConfigBase"};


    /**
     * 去除后面的后缀 如providerConfig->去除后 provider
     * @param cls
     * @return
     */
    public static String getTagName(Class<?> cls) {
        String tag = cls.getSimpleName();
        for (String suffix : SUFFIXES) {
            if (tag.endsWith(suffix)) {
                tag = tag.substring(0, tag.length() - suffix.length());
                break;
            }
        }
        return StringUtils.camelToSplitName(tag, "-");

    }

    /**
     * 往hashmap中添加符合格式变量
     *
     * @param parameters
     * @param config
     * @param prefix
     */
    public static void appendParameters(Map<String, String> parameters, Object config, String prefix) {
        if (config == null) {
            return;
        }
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {

            try {
                if (MethodUtils.isGetter(method)) {
                    Parameter parameter = method.getAnnotation(Parameter.class);
                    String key = null;
                    String name = method.getName();

                    //注解返利类是Object对象的 或者配置exclued属性的直接跳过
                    if (method.getReturnType() == Object.class || parameter != null && parameter.excluded()) {
                        continue;
                    }
                    if (parameter != null && parameter.key().length() > 0) {
                        key = parameter.key();
                    } else {
                        //"没有配置注解"
                        key = calculatePropertyFromGetter(name);
                    }
                    //调用方法获取值
                    //TODO 源码没有权限
                    method.setAccessible(true);

                    Object value = method.invoke(config);
                    String str = String.valueOf(value).trim();
                    if (value != null && str.length() > 0) {
                        //根据注解属性解析处理 配置逃脱属性加密？
                        if (parameter != null && parameter.escaped()) {
                            str = URL.encode(str);
                        }
                        //直接添加
                        if (parameter != null && parameter.append()) {
                            String pre = parameters.get(key);
                            if (pre != null && pre.length() > 0) {
                                str = pre + "," + str;
                            }
                        }
                        //没有特殊配置的 就是key+prefix
                        if (prefix != null && prefix.length() > 0) {
                            key = prefix + "." + key;
                        }
                        parameters.put(key, str);

                    } else if (parameter != null && parameter.required()) {
                        throw new IllegalStateException(config.getClass().getSimpleName() + "." + key + " == null");
                    }
                } else if (isParametersGetter(method)) {
                    //处理
                    method.setAccessible(true);
                    Map<String, String> map = (Map<String, String>) method.invoke(config, new Object[0]);
                    parameters.putAll(convert(map, prefix));
                }


            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }


        }

    }

    private static String calculatePropertyFromGetter(String name) {
        //可能是get 就3开头 is 2
        int i = name.startsWith("get") ? 3 : 2;
        return StringUtils.camelToSplitName(name.substring(i, i + 1).toLowerCase() + name.substring(i + 1), ".");
    }

    private static boolean isParametersGetter(Method method) {
        String name = method.getName();
        return ("getParameters".equals(name)
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0
                && method.getReturnType() == Map.class);
    }

    private static Map<String, String> convert(Map<String, String> parameters, String prefix) {
        if (parameters == null || parameters.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();

        // prefix 为空 就是空格, 非空则是 prefix.
        String pre = (prefix != null && prefix.length() > 0 ? prefix + "." : "");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            result.put(pre + key, value);
            // For compatibility, key like "registry-type" will has a duplicate key "registry.type"
            //将 - 替换 .
            if (key.contains("-")) {
                result.put(pre + key.replace('-', '.'), value);
            }
        }

        return result;
    }

    @Deprecated
    public static void appendAttributes(Map<String, Object> parameters, Object config) {
        appendAttributes(parameters, config, null);
    }

    @Deprecated
    public static void appendAttributes(Map<String, Object> parameters, Object config, String prefix) {
        if (config == null) {
            return;
        }
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {
            try {
                Parameter parameter = method.getAnnotation(Parameter.class);
                if (parameter == null || !parameter.attribute()) {
                    continue;
                }
                String name = method.getName();
                if (MethodUtils.isGetter(method)) {
                    String key;
                    if (parameter.key().length() > 0) {
                        key = parameter.key();
                    } else {
                        key = calculateAttributeFromGetter(name);
                    }
                    method.setAccessible(true);
                    Object value = method.invoke(config);
                    if (value != null) {
                        if (prefix != null && prefix.length() > 0) {
                            key = prefix + "." + key;
                        }
                        parameters.put(key, value);
                    }
                }

            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }


    @Parameter(excluded = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void updateIdIfAbsent(String value) {
        if (StringUtils.isNotEmpty(value) && StringUtils.isEmpty(id)) {
            this.id = value;
        }
    }


    private static String calculateAttributeFromGetter(String getter) {
        int i = getter.startsWith("get") ? 3 : 2;
        return getter.substring(i, i + 1).toLowerCase() + getter.substring(i + 1);
    }

}