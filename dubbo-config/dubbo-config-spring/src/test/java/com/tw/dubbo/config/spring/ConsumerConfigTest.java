package com.tw.dubbo.config.spring;

import com.tw.dubbo.common.config.ConsumerConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * ConsumerConfig
 * @author clay
 * @date 2020/1/30 1:17
 */
public class ConsumerConfigTest {


//    @Test
//    public void testTimeout() throws Exception {
//        try {
//            System.clearProperty("sun.rmi.transport.tcp.responseTimeout");
//            ConsumerConfig consumer = new ConsumerConfig();
//            consumer.setTimeout(10);
//            assertThat(consumer.getTimeout(), is(10));
//            assertThat(System.getProperty("sun.rmi.transport.tcp.responseTimeout"), equalTo("10"));
//        } finally {
//            System.clearProperty("sun.rmi.transport.tcp.responseTimeout");
//        }
//    }



    @Test
    public void testDefault() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setDefault(true);
        assertThat(consumer.isDefault(), is(true));
    }

    @Test
    public void testClient() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setClient("client");
        assertThat(consumer.getClient(), equalTo("client"));
    }

    @Test
    public void testThreadpool() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setThreadpool("fixed");
        assertThat(consumer.getThreadpool(), equalTo("fixed"));
    }

    @Test
    public void testCorethreads() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setCorethreads(10);
        assertThat(consumer.getCorethreads(), equalTo(10));
    }

    @Test
    public void testThreads() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setThreads(20);
        assertThat(consumer.getThreads(), equalTo(20));
    }

    @Test
    public void testQueues() throws Exception {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setQueues(5);
        assertThat(consumer.getQueues(), equalTo(5));
    }

}
