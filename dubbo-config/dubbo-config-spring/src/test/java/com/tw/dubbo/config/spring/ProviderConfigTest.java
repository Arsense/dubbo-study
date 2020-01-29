package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.ProviderConfig;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author clay
 * @date 2020/1/27 16:30
 */
public class ProviderConfigTest {

    @Test
    public void testProtocol() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setProtocol("protocol");
        assertThat(provider.getProtocol().getName(), equalTo("protocol"));
    }


    @Test
    public void testHost() throws Exception {
        //TODO NOT OK
        ProviderConfig provider = new ProviderConfig();
        provider.setHost("demo-host");
        Map<String, String> parameters = new HashMap<String, String>();
        ProviderConfig.appendParameters(parameters, provider);
        assertThat(provider.getHost(), equalTo("demo-host"));
        assertThat(parameters, not(hasKey("host")));
    }

    @Test
    public void testPort() throws Exception {
        //TODO NOT OK
        ProviderConfig provider = new ProviderConfig();
        provider.setPort(8080);
        Map<String, String> parameters = new HashMap<String, String>();
        ProviderConfig.appendParameters(parameters, provider);
        assertThat(provider.getPort(), is(8080));
        assertThat(parameters, not(hasKey("port")));
    }

//    @Test
//    public void testPath() throws Exception {
//        ProviderConfig provider = new ProviderConfig();
//        provider.setPath("/path");
//        Map<String, String> parameters = new HashMap<String, String>();
//        ProviderConfig.appendParameters(parameters, provider);
//        assertThat(provider.getPath(), equalTo("/path"));
//        assertThat(provider.getContextpath(), equalTo("/path"));
//        assertThat(parameters, not(hasKey("path")));
//    }

    @Test
    public void testContextPath() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setContextpath("/context-path");
        Map<String, String> parameters = new HashMap<String, String>();
        ProviderConfig.appendParameters(parameters, provider);
        assertThat(provider.getContextpath(), equalTo("/context-path"));
        assertThat(parameters, not(hasKey("/context-path")));
    }

    @Test
    public void testThreadpool() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setThreadpool("mockthreadpool");
        assertThat(provider.getThreadpool(), equalTo("mockthreadpool"));
    }

    @Test
    public void testThreads() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setThreads(10);
        assertThat(provider.getThreads(), is(10));
    }

    @Test
    public void testIothreads() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setIothreads(10);
        assertThat(provider.getIothreads(), is(10));
    }

    @Test
    public void testQueues() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setQueues(10);
        assertThat(provider.getQueues(), is(10));
    }

    @Test
    public void testAccepts() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setAccepts(10);
        assertThat(provider.getAccepts(), is(10));
    }

//    @Test
//    public void testCharset() throws Exception {
//        ProviderConfig provider = new ProviderConfig();
//        provider.setCharset("utf-8");
//        assertThat(provider.getCharset(), equalTo("utf-8"));
//    }

    @Test
    public void testServer() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setServer("demo-server");
        assertThat(provider.getServer(), equalTo("demo-server"));
    }

    @Test
    public void testClient() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setClient("client");
        assertThat(provider.getClient(), equalTo("client"));
    }

    @Test
    public void testTelnet() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setTelnet("mocktelnethandler");
        assertThat(provider.getTelnet(), equalTo("mocktelnethandler"));
    }
    @Test
    public void testStatus() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setStatus("mockstatuschecker");
        assertThat(provider.getStatus(), equalTo("mockstatuschecker"));
    }

    @Test
    public void testWait() throws Exception {
        ProviderConfig provider = new ProviderConfig();
        provider.setWait(10);
        assertThat(provider.getWait(), equalTo(10));
    }

}
