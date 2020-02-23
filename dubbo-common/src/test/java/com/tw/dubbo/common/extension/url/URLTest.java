package com.tw.dubbo.common.extension.url;

import com.tw.dubbo.common.URLBuilder;
import com.tw.dubbo.common.utils.URL;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/** url方法测试类
 * @author clay
 * @date 2020/2/23 16:33
 */
public class URLTest {

    @Test
    public void testNoArgConstructor() {
        URL url = new URLBuilder().build();
        assertThat(url.toString(), equalTo(""));
    }
}
