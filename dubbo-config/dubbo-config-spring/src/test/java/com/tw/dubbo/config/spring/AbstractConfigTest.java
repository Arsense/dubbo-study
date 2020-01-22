package com.tw.dubbo.config.spring;

import com.alibaba.fastjson.JSON;
import com.tw.dubbo.common.config.Parameter;
import com.tw.dubbo.config.AbstractConfig;
import com.tw.dubbo.config.spring.api.Greeting;
import com.tw.dubbo.config.util.ConfigValidationUtils;
import org.junit.Assert;
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
    public void testAppendParameters1() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("num", "ONE");

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


    @Test
    public void testAppendParameters2() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Map<String, String> parameters = new HashMap<String, String>();
            AbstractConfig.appendParameters(parameters, new ParameterConfig());
        });
    }

    @Test
    public void testAppendParameters3() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        AbstractConfig.appendParameters(parameters, null);
        Assert.assertTrue(parameters.isEmpty());
//        assertTrue();
    }

    @Test
    public void testAppendParameters4() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();

        AbstractConfig.appendParameters(parameters, new ParameterConfig(1, "hello/world", 30, "password"));
        Assertions.assertEquals("one", parameters.get("key.1"));
        Assertions.assertEquals("two", parameters.get("key.2"));
        Assertions.assertEquals("1", parameters.get("num"));
        Assertions.assertEquals("hello%2Fworld", parameters.get("naming"));
        Assertions.assertEquals("30", parameters.get("age"));
    }


    /**
     * 测试属性解析  传前缀prefix
     * @throws Exception
     */
    @Test
    public void testAppendAttributes1() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        AbstractConfig.appendAttributes(parameters, new AttributeConfig('l', true, (byte) 0x01), "prefix");
        Assertions.assertEquals('l', parameters.get("prefix.let"));
        Assertions.assertEquals(true, parameters.get("prefix.activate"));
        Assertions.assertFalse(parameters.containsKey("prefix.flag"));
    }


    /**
     * 测试属性解析 不传前缀
     * @throws Exception
     */
    @Test
    public void testAppendAttributes2() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        AbstractConfig.appendAttributes(parameters, new AttributeConfig('l', true, (byte) 0x01));
        Assertions.assertEquals('l', parameters.get("let"));
        Assertions.assertEquals(true, parameters.get("activate"));
        Assertions.assertFalse(parameters.containsKey("flag"));
    }

    @Test
    public void checkExtension() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> ConfigValidationUtils.checkExtension(Greeting.class, "hello", "world"));
    }

    @Test
    public void checkMultiExtension1() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> ConfigValidationUtils.checkMultiExtension(Greeting.class, "hello", "default,world"));
    }

    @Test
    public void checkMultiExtension2() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> ConfigValidationUtils.checkMultiExtension(Greeting.class, "hello", "default,-world"));
    }


    @Test
    public void checkLength() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i <= 200; i++) {
                builder.append("a");
            }
            ConfigValidationUtils.checkLength("hello", builder.toString());
        });
    }

    //不太清楚 这些是避免什么场景

    @Test
    public void checkPathLength() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i <= 200; i++) {
                builder.append("a");
            }
            ConfigValidationUtils.checkPathLength("hello", builder.toString());
        });
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

        @Parameter(key = "naming", append = true, escaped = true, required = true)
        public String getName() {
            return name;
        }

        public Map getParameters() {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key.1", "one");
            map.put("key-2", "two");
            return map;
        }

        /**
         *
         * @param name
         */
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

    private static class AttributeConfig {
        private char letter;
        private boolean activate;
        private byte flag;

        public AttributeConfig(char letter, boolean activate, byte flag) {
            this.letter = letter;
            this.activate = activate;
            this.flag = flag;
        }

        @Parameter(attribute = true, key = "let")
        public char getLetter() {
            return letter;
        }

        public void setLetter(char letter) {
            this.letter = letter;
        }

        @Parameter(attribute = true)
        public boolean isActivate() {
            return activate;
        }

        public void setActivate(boolean activate) {
            this.activate = activate;
        }

        public byte getFlag() {
            return flag;
        }

        public void setFlag(byte flag) {
            this.flag = flag;
        }
    }

}
