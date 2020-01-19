package com.tw.dubbo.config;

import com.tw.dubbo.common.config.Parameter;
import com.tw.dubbo.common.util.URL;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
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
    private Boolean isDefault;

    private static class ParameterConfig {
        ParameterConfig() {

        }
    }

    /**
     * 往hashmap中添加符合格式变量
     * @param parameters
     * @param config
     * @param prefix
     */
    public static void appendParameters(Map<String, String> parameters, Object config, String prefix){
        if (config == null) {
            return;
        }
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {


            try {
                if (MethodUtils.isGetter(method)) {
                    Parameter parameter = method.getAnnotation(Parameter.class);
                    String key = null;
                    //注解返利类是Object对象的 或者配置exclued属性的直接跳过
                    if (method.getReturnType() == Object.class || parameter != null && parameter.excluded()) {
                        continue;
                    }
                    if (parameter != null && parameter.key().length() > 0) {
                        key = parameter.key();
                    } else {
                        System.out.println(method.getName() + "没有配置注解");
//                key = calculatePropertyFromGetter(name);
                    }
                    //调用方法获取值
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
                }
//                } else if (isParametersGetter(method)) {
//                    //处理
//                    Map<String, String> map = (Map<String, String>) method.invoke(config, new Object[0]);
//                    parameters.putAll(convert(map, prefix));
//                }


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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
