package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.MethodConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author tangwei
 * @date 2020/1/29 12:08
 */
public class MethodConfigTest {

    @Test
    public void testName() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setName("hello");
        assertThat(method.getName(), equalTo("hello"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters, not(hasKey("name")));
    }


}
