package com.tw.dubbo.common.extension.url;

import com.tw.dubbo.common.URLBuilder;
import com.tw.dubbo.common.utils.URL;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * url构造类测试类
 * @author clay
 * @date 2020/2/23 16:33
 */
public class URLBuilderTest {


    @Test
    public void testNoArgConstructor() {
        URL url = new URLBuilder().build();
        assertThat(url.toString(), equalTo(""));
    }
    @Test
    public void shouldAddParameter() {
        URL url1 = URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan");

        URL url2 = URLBuilder.from(url1)
                .addParameter("newKey1", "newValue1") // string
                .addParameter("newKey2", 2) // int
                .addParameter("version", 1) // override
                .build();
        assertThat(url2.getParameter("newKey1"), equalTo("newValue1"));
        assertThat(url2.getParameter("newKey2"), equalTo("2"));
        assertThat(url2.getParameter("version"), equalTo("1"));
    }


    /**
     * 覆盖参数测试
     */
    @Test
    public void shouldSet() {
        URL url1 = URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan");
        URL url2 = URLBuilder.from(url1)
                .setProtocol("rest")
                .setUsername("newUsername")
                .setPassword("newPassword")
                .setHost("newHost")
                .setPath("newContext")
                .setPort(1234)
                .build();
        assertThat(url2.getProtocol(), equalTo("rest"));
        assertThat(url2.getUsername(), equalTo("newUsername"));
        assertThat(url2.getPassword(), equalTo("newPassword"));
        assertThat(url2.getHost(), equalTo("newHost"));
        assertThat(url2.getPort(), equalTo(1234));
        assertThat(url2.getPath(), equalTo("newContext"));

        url2 = URLBuilder.from(url1)
                .setAddress("newHost2:2345")
                .build();
        assertThat(url2.getHost(), equalTo("newHost2"));
        assertThat(url2.getPort(), equalTo(2345));
    }

    /**
     * 清楚所有参数
     */
    @Test
    public void shouldClearParameters() {
        URL url1 = URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan");
        URL url2 = URLBuilder.from(url1)
                .clearParameters()
                .build();
        assertThat(url2.getParameters().size(), equalTo(0));
    }

    /**
     * 单个参数移除
     */
    @Test
    public void shouldRemoveParameters() {
        URL url1 = URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan&key2=v2");
        URL url2 = URLBuilder.from(url1)
                .removeParameters(Arrays.asList("key2", "application"))
                .build();
        assertThat(url2.getParameters().size(), equalTo(1));
        assertThat(url2.getParameter("version"), equalTo("1.0.0"));
    }

    /**
     * 测试不存在才添加的方式
     */
    @Test
    public void shouldAddIfAbsent() {
        URL url1 = URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan&key2=v2");
        URL url2 = URLBuilder.from(url1)
                .addParameterIfAbsent("absentKey", "absentValue")
                .addParameterIfAbsent("version", "2.0.0") // should not override
                .build();
        assertThat(url2.getParameter("version"), equalTo("1.0.0"));
        assertThat(url2.getParameter("absentKey"), equalTo("absentValue"));
    }
}
