package com.tw.dubbo.common.extension.ex8_add.impl;

import com.tw.dubbo.common.extension.Adaptive;
import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.extension.ex8_add.AddExt2;
import com.tw.dubbo.common.utils.URL;

/**
 * @author clay
 * @date 2020/2/23 13:34
 */
@Adaptive
public class AddExt2_ManualAdaptive  implements AddExt2 {

    public String echo(URL url, String s) {
        AddExt2 addExt1 = ExtensionLoader.getExtensionLoader(AddExt2.class).getExtension(url.getParameter("add.ext2"));
        return addExt1.echo(url, s);
    }
}
