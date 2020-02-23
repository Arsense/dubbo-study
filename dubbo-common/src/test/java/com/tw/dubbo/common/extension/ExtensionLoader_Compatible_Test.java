package com.tw.dubbo.common.extension;

import com.tw.dubbo.common.extension.compatible.CompatibleExt;
import com.tw.dubbo.common.extension.compatible.impl.CompatibleExtImpl1;
import com.tw.dubbo.common.extension.compatible.impl.CompatibleExtImpl2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author clay
 * @date 2020/2/23 16:30
 */
public class ExtensionLoader_Compatible_Test {
    @Test
    public void test_getExtension() throws Exception {
        assertTrue(ExtensionLoader.getExtensionLoader(CompatibleExt.class).getExtension("impl1") instanceof CompatibleExtImpl1);
        assertTrue(ExtensionLoader.getExtensionLoader(CompatibleExt.class).getExtension("impl2") instanceof CompatibleExtImpl2);
    }
}
