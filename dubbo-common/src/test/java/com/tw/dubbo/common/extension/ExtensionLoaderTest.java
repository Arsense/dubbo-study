package com.tw.dubbo.common.extension;

import com.tw.dubbo.common.extension.ext1.SimpleExt;
import com.tw.dubbo.common.extension.ext1.impl.SimpleExtImpl1;
import com.tw.dubbo.common.extension.support.NoSpiExt;
import org.junit.jupiter.api.Test;

import static com.tw.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author clay
 * @date 2020/2/5 1:17
 */
public class ExtensionLoaderTest {

    @Test
    public void test_getExtensionLoader_Null() throws Exception {
        try {
            getExtensionLoader(null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    containsString("Extension type == null"));
        }
    }



    @Test
    public void test_getExtensionLoader_NotInterface() throws Exception {
        try {
            getExtensionLoader(ExtensionLoaderTest.class);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    containsString("Extension type (class org.apache.dubbo.common.extension.ExtensionLoaderTest) is not an interface"));
        }
    }

    @Test
    public void test_getExtensionLoader_NotSpiAnnotation() throws Exception {
        try {
            getExtensionLoader(NoSpiExt.class);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    allOf(containsString("org.apache.dubbo.common.extension.NoSpiExt"),
                            containsString("is not an extension"),
                            containsString("NOT annotated with @SPI")));
        }
    }


    /**
     * 上面全是测试异常情况 现在才是测试正常的
     * @throws Exception
     */
    @Test
    public void test_getDefaultExtension() throws Exception {
        //第一步主要是初始哈objectFactory这个参数
        ExtensionLoader<SimpleExt> getExtensionLoader = getExtensionLoader(SimpleExt.class);

        SimpleExt ext = getExtensionLoader.getDefaultExtension();
        assertThat(ext, instanceOf(SimpleExtImpl1.class));

        String name = getExtensionLoader.getDefaultExtensionName();
        assertEquals("impl1", name);
    }
}
