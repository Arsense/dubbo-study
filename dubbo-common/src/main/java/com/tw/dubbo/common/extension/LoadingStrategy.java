package com.tw.dubbo.common.extension;

/**
 * 加载SPI的类的策略模式接口类
 * @author clay
 * @date 2020/2/9 1:58
 */
public interface LoadingStrategy {
    /**
     * 需要扫描的路径
     * @return
     */
    String directory();

    default boolean preferExtensionClassLoader() {
        return false;
    }

    /**
     * 需要排除扫描的路径
     * @return
     */
    default String[] excludedPackages() {
        return null;
    }

}
