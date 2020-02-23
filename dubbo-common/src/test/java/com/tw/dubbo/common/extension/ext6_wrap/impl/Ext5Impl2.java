package com.tw.dubbo.common.extension.ext6_wrap.impl;

import com.tw.dubbo.common.extension.ext6_wrap.WrappedExt;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/17 1:02
 */
public class Ext5Impl2 implements WrappedExt {
    public String echo(URL url, String s) {
        return "Ext5Impl2-echo";
    }

}
