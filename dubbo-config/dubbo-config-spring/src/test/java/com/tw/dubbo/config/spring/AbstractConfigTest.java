package com.tw.dubbo.config.spring;

import com.alibaba.fastjson.JSON;
import com.tw.dubbo.common.config.Parameter;
import com.tw.dubbo.config.AbstractConfig;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clay
 * @date 2020/1/19 10:22
 */
public class AbstractConfigTest {

    /**
     * 测试注解配置key规则 解析到容器中
     * @throws Exception
     */
    @Test
    public void testAppendParameters1() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        AbstractConfig.appendParameters(parameters, new ParameterConfig(1, "hello/world", 30, "password"), "prefix");

        System.out.println("testAppendParameters1结果是================="
                + JSON.toJSON(parameters));
        Assertions.assertEquals("one", parameters.get("prefix.key.1"));
        Assertions.assertEquals("two", parameters.get("prefix.key.2"));
        Assertions.assertEquals("ONE,1", parameters.get("prefix.num"));
        Assertions.assertEquals("hello%2Fworld", parameters.get("prefix.naming"));
        Assertions.assertEquals("30", parameters.get("prefix.age"));
        Assertions.assertTrue(parameters.containsKey("prefix.key-2"));
        Assertions.assertTrue(parameters.containsKey("prefix.key.2"));
        Assertions.assertFalse(parameters.containsKey("prefix.secret"));



    }
    private static class ParameterConfig {
        private int number;
        private String name;
        private int age;
        private String secret;

        ParameterConfig() {
        }

        ParameterConfig(int number, String name, int age, String secret) {
            this.number = number;
            this.name = name;
            this.age = age;
            this.secret = secret;
        }

        @Parameter(key = "num", append = true)
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         */
        @Parameter(key = "naming", append = true, escaped = true, required = true)
        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Parameter(excluded = true)
        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

}
