package com.tw.dubbo.config;

/**
 * <pre>
 *     属性太多挤不下 都放都公共的抽象类里 节省代码量
 * </pre>
 *
 *
 * @author tangwei
 * @date 2018/12/5 14:59
 */
public class AbstractConfig {

    protected String id;
    //是否需要默认属性配置
    private Boolean isDefault;

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
