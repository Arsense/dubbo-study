package com.tw.dubbo.common.extension.ext6_wrap.impl;

import com.tw.dubbo.common.extension.ext6_wrap.WrappedExt;
import com.tw.dubbo.common.utils.URL;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author clay
 * @date 2020/2/17 0:59
 */
public class Ext5Wrapper1 implements WrappedExt  {

    public static AtomicInteger echoCount = new AtomicInteger();
    WrappedExt instance;

    public Ext5Wrapper1(WrappedExt instance) {
        this.instance = instance;
    }

    @Override
    public String echo(URL url, String s) {
        echoCount.incrementAndGet();
        return instance.echo(url, s);
    }
}
