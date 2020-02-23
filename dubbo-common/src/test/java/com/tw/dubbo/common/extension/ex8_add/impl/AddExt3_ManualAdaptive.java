package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.extension.ex8_add.AddExt3;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/23 16:00
 */
@Adaptive
public class AddExt3_ManualAdaptive implements AddExt3 {
    public String echo(URL url, String s) {
        AddExt3 addExt1 = ExtensionLoader.getExtensionLoader(AddExt3.class).getExtension(url.getParameter("add.ext3"));
        return addExt1.echo(url, s);
    }
}
