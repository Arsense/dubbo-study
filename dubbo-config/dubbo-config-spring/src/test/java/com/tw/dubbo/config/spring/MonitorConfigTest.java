package com.tw.dubbo.config.spring;

import com.tw.dubbo.config.MonitorConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
/**
 * 监控配置类config
 * @author clay
 * @date 2020/1/24 1:21
 */
public class MonitorConfigTest {

    @Test
    public void testAddress() throws Exception {
        MonitorConfig monitor = new MonitorConfig();
        monitor.setAddress("monitor-addr");
        assertThat(monitor.getAddress(), equalTo("monitor-addr"));
        Map<String, String> parameters = new HashMap<String, String>();
        MonitorConfig.appendParameters(parameters, monitor);
        assertThat(parameters.isEmpty(), is(true));
    }




}
