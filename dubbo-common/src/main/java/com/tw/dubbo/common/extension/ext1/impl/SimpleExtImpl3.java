package com.tw.dubbo.common.extension.ext1.impl;

import com.tw.dubbo.common.extension.ext1.SimpleExt;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/7 17:42
 */
public class SimpleExtImpl3 implements SimpleExt {

    public String echo(URL url, String s) {
        return "Ext1Impl3-echo";
    }

    public String yell(URL url, String s) {
        return "Ext1Impl3-yell";
    }

    public String bang(URL url, int i) {
        return "bang3";
    }
}
