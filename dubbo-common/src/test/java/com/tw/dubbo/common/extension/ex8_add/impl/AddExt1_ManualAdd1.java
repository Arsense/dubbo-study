package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.ex8_add.AddExt1;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/23 2:06
 */
public class AddExt1_ManualAdd1 implements AddExt1 {
    @Override
    public String echo(URL url, String s) {
        return this.getClass().getSimpleName();
    }

}
