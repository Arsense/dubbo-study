package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.ex8_add.AddExt2;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/23 16:00
 */
public class AddExt2Impl1 implements AddExt2 {
    public String echo(URL url, String s) {
        return this.getClass().getSimpleName();
    }
}