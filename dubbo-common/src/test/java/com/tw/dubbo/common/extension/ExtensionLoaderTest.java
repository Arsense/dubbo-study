package com.tw.dubbo.common.extension;

import com.tw.dubbo.common.extension.ext1.SimpleExt;
import com.tw.dubbo.common.extension.ext1.impl.SimpleExtImpl1;
import com.tw.dubbo.common.extension.ext1.impl.SimpleExtImpl2;
import com.tw.dubbo.common.extension.ext6_wrap.WrappedExt;
import com.tw.dubbo.common.extension.ext6_wrap.impl.Ext5Wrapper1;
import com.tw.dubbo.common.extension.ext6_wrap.impl.Ext5Wrapper2;
import com.tw.dubbo.common.extension.support.NoSpiExt;
import org.junit.jupiter.api.Test;

import static com.tw.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

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

        String name = getExtensionLoader(SimpleExt.class).getDefaultExtensionName();
        assertEquals("impl1", name);
    }


    @Test
    public void test_getExtension() throws Exception {
        assertTrue(getExtensionLoader(SimpleExt.class).getExtension("impl1") instanceof SimpleExtImpl1);
        assertTrue(getExtensionLoader(SimpleExt.class).getExtension("impl2") instanceof SimpleExtImpl2);
    }


    @Test
    public void test_getExtension_WithWrapper() throws Exception {
        WrappedExt impl1 = getExtensionLoader(WrappedExt.class).getExtension("impl1");
        assertThat(impl1, anyOf(instanceOf(Ext5Wrapper1.class), instanceOf(Ext5Wrapper2.class)));

        WrappedExt impl2 = getExtensionLoader(WrappedExt.class).getExtension("impl2");
        assertThat(impl2, anyOf(instanceOf(Ext5Wrapper1.class), instanceOf(Ext5Wrapper2.class)));


//        URL url = new URL("p1", "1.2.3.4", 1010, "path1");
//        int echoCount1 = Ext5Wrapper1.echoCount.get();
//        int echoCount2 = Ext5Wrapper2.echoCount.get();
//
//        assertEquals("Ext5Impl1-echo", impl1.echo(url, "ha"));
//        assertEquals(echoCount1 + 1, Ext5Wrapper1.echoCount.get());
//        assertEquals(echoCount2 + 1, Ext5Wrapper2.echoCount.get());
    }
}
