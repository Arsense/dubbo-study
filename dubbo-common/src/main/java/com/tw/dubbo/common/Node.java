package com.tw.dubbo.common;

import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/3 0:24
 *
 * Node. (API/SPI, Prototype, ThreadSafe)
 */
public interface Node {


    /**
     * get url.
     *
     * @return url.
     */
    URL getUrl();

    /**
     * is available.
     *
     * @return available.
     */
    boolean isAvailable();

    /**
     * destroy.
     */
    void destroy();


}
