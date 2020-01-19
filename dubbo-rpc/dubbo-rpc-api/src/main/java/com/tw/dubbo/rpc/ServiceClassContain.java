package com.tw.dubbo.rpc;

/**
 * @author clay
 * @date 2018/11/30 10:54
 */
public class ServiceClassContain {

    private static final ServiceClassContain INSTANCE = new ServiceClassContain();

    public static ServiceClassContain getInstance() {
        return INSTANCE;
    }

    private final ThreadLocal<Class> container  = new ThreadLocal<Class>();

    public Class popServiceClass() {
        Class clazz = container.get();
        container.remove();
        return clazz;
    }

    public void pushServiceClass(Class clazz) {
        container.set(clazz);
    }
}
