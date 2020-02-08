package com.tw.dubbo.common.extension.support;

import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/5 1:22
 *
 * Has no SPI annotation
 */
public interface NoSpiExt {
//    @Adaptive
    String echo(URL url, String s);
}
