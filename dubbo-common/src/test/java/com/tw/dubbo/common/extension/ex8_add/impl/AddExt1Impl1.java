package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.ex8_add.AddExt1;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/23 15:59
 */
public class AddExt1Impl1 implements AddExt1 {
    @Override
    public String echo(URL url, String s) {
        return this.getClass().getSimpleName();
    }

}
