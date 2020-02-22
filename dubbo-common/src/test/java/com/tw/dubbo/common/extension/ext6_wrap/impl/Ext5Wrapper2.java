package com.tw.dubbo.common.extension.ext6_wrap.impl;

import com.tw.dubbo.common.extension.ext6_wrap.WrappedExt;
import com.tw.dubbo.common.utils.URL;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwei
 * @date 2020/2/17 1:00
 */
public class Ext5Wrapper2 implements WrappedExt {


    public static AtomicInteger echoCount = new AtomicInteger();
    WrappedExt instance;

    public Ext5Wrapper2(WrappedExt instance) {
        this.instance = instance;
    }

    public String echo(URL url, String s) {
        echoCount.incrementAndGet();
        return instance.echo(url, s);
    }
}
