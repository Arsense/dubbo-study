package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.extension.ex8_add.AddExt1;
import com.tw.dubbo.common.utils.URL;

/**
 * @author tangwei
 * @date 2020/2/23 2:04
 */
@Adaptive
public class AddExt1_ManualAdaptive implements AddExt1 {
    public String echo(URL url, String s) {
        AddExt1 addExt1 = ExtensionLoader.getExtensionLoader(AddExt1.class).getExtension(url.getParameter("add.ext1"));
        return addExt1.echo(url, s);
    }
}