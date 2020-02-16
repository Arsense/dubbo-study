package com.tw.dubbo.common.extension.ext1;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.SPI;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/7 17:40
 */
@SPI("impl1")
public interface SimpleExt {
    // @Adaptive example, do not specify a explicit key.
    @Adaptive
    String echo(URL url, String s);

    @Adaptive({"key1", "key2"})
    String yell(URL url, String s);

    // no @Adaptive
    String bang(URL url, int i);
}
