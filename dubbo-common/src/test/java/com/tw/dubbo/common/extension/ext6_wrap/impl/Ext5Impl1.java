package com.tw.dubbo.common.extension.ext6_wrap.impl;

import com.tw.dubbo.common.extension.ext6_wrap.WrappedExt;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/17 1:01
 */
public class Ext5Impl1  implements WrappedExt {
    public String echo(URL url, String s) {
        return "Ext5Impl1-echo";
    }

}
