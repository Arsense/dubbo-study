package com.tw.dubbo.common.config;

/**
 * @author tangwei
 * @date 2020/1/30 17:47
 */
public abstract class ServiceConfigBase<T> extends AbstractInterfaceConfig {


    /**
     * 接口实现
     */
    protected T ref;
    /**
     * 接口类型
     */
    protected Class<?> interfaceClass;
    /**
     * 接口名
     */
    protected String interfaceName;

    /**
     * 提供者
     */
    protected ProviderConfig provider;

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }
}
