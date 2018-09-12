package com.remote.test.consumer;

import com.remote.test.utils.Request;
import com.remote.test.utils.Response;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * 这里发生多线程调用
 * @author tangwei
 * @date 2018/9/12 14:08
 */
public class RevokerServiceCallable implements Callable<Response> {

    private static final Logger LOG = LoggerFactory.getLogger(RevokerServiceCallable.class);

    private Channel channel;
    private InetSocketAddress inetSocketAddress;
    private Request request;

    public RevokerServiceCallable(InetSocketAddress inetSocketAddress, Request request) {


    }

    public static RevokerServiceCallable of(InetSocketAddress inetSocketAddress, Request request) {
        return new RevokerServiceCallable(inetSocketAddress, request);
    }
    @Override
    public Response call() throws Exception {
        return null;
    }
}
