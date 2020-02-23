package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.extension.ex8_add.AddExt4;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/23 16:00
 */
@Adaptive
public class AddExt4_ManualAdaptive implements AddExt4 {
    public String echo(URL url, String s) {
        AddExt4 addExt1 = ExtensionLoader.getExtensionLoader(AddExt4.class).getExtension(url.getParameter("add.ext4"));
        return addExt1.echo(url, s);
    }
}