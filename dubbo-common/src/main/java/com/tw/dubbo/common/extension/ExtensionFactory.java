package com.tw.dubbo.common.extension;

/**
 * @author clay
 * @date 2020/2/7 18:26
 */
@SPI
public interface ExtensionFactory {

    /**
     * Get extension.
     *
     * @param type object type.
     * @param name object na    me.
     * @return object instance.
     */
    <T> T getExtension(Class<T> type, String name);

}
