package com.tw.dubbo.common.extension.ext6_wrap;

import com.tw.dubbo.common.extension.SPI;
import com.tw.dubbo.common.utils.URL;
/**
 * @author clay
 * @date 2020/2/17 0:58
 */
@SPI("impl1")
public interface WrappedExt {
    String echo(URL url, String s);

}
