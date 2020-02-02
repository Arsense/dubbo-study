package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.AbstractServiceConfig;
import com.tw.dubbo.common.config.ArgumentConfig;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author clay
 * @date 2020/2/2 17:00
 */
public class ArgumentConfigTest {

    @Test
    public void testIndex() throws Exception {
        ArgumentConfig argument = new ArgumentConfig();
        argument.setIndex(1);
        assertThat(argument.getIndex(), is(1));
    }

    @Test
    public void testType() throws Exception {
        ArgumentConfig argument = new ArgumentConfig();
        argument.setType("int");
        assertThat(argument.getType(), equalTo("int"));
    }

    @Test
    public void testCallback() throws Exception {
        ArgumentConfig argument = new ArgumentConfig();
        argument.setCallback(true);
        assertThat(argument.isCallback(), is(true));
    }

    @Test
    public void testArguments() throws Exception {
        ArgumentConfig argument = new ArgumentConfig();
        argument.setIndex(1);
        argument.setType("int");
        argument.setCallback(true);
        Map<String, String> parameters = new HashMap<String, String>();
        AbstractServiceConfig.appendParameters(parameters, argument);
        assertThat(parameters, hasEntry("callback", "true"));
        assertThat(parameters.size(), is(1));
    }
}
