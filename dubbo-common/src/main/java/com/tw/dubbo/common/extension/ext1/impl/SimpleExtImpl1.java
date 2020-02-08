package com.tw.dubbo.common.extension.ext1.impl;

import com.tw.dubbo.common.extension.ext1.SimpleExt;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/7 17:40
 */
public class SimpleExtImpl1 implements SimpleExt {

    public String echo(URL url, String s) {
        return "Ext1Impl1-echo";
    }

    public String yell(URL url, String s) {
        return "Ext1Impl1-yell";
    }

    public String bang(URL url, int i) {
        return "bang1";
    }

}
