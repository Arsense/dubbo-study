package com.remote.test.consumer;

import com.remote.test.netty.NettyCosumeChannelQueue;
import com.remote.test.utils.Request;
import com.remote.test.utils.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
        this.inetSocketAddress = inetSocketAddress;
        this.request = request;

    }

    public static RevokerServiceCallable of(InetSocketAddress inetSocketAddress, Request request) {
        return new RevokerServiceCallable(inetSocketAddress, request);
    }
    @Override
    public Response call() throws Exception {
        //初始化返回结果容器,将本次调用的唯一标识作为Key存入返回结果的Map
        ResponseHelper.initResponseData(request.getUniqueKey());
        //根据本地调用服务提供者地址获取对应的Netty通道channel队列

        ArrayBlockingQueue<Channel> blockingQueue = NettyCosumeChannelQueue.singleton().acquire(inetSocketAddress);
        try {

            if (channel == null) {
                //从队列中获取本次调用的Netty通道channel
                channel = blockingQueue.poll(request.getTimeout(), TimeUnit.MILLISECONDS);
            }
            //若获取的channel通道已经不可用,则重新获取一个
            while (!channel.isOpen() || !channel.isActive() || !channel.isWritable()) {
                LOG.warn("----------retry get new Channel------------");
                channel = blockingQueue.poll(request.getTimeout(), TimeUnit.MILLISECONDS);
                if (channel == null) {
                    //若队列中没有可用的Channel,则重新注册一个Channel
                    channel = NettyCosumeChannelQueue.singleton().registerChannel(inetSocketAddress);
                }

            }

            //将本次调用的信息写入Netty通道,发起异步调用
            System.out.println("call   client when write, channel ===>" + channel);
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.syncUninterruptibly();
            //从返回结果容器中获取返回结果,同时设置等待超时时间为invokeTimeout
            return ResponseHelper.getValue(request.getUniqueKey());

        } catch (Exception e) {
            LOG.error("service invoke error.", e);
        }finally {
            //本次调用完毕后,将Netty的通道channel重新释放到队列中,以便下次调用复用
        }


        return null;
    }
}
