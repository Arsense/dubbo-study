package com.tw.dubbo.remoting.netty;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * @author tangwei
 * @date 2018/12/5 12:20
 */
public class NettyCodecAdapter {
    private final ChannelHandler encoder = new InternalEncoder();

    private final ChannelHandler decoder = new InternalDecoder();

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }



    private class InternalEncoder extends OneToOneEncoder {
        @Override
        protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
            return null;
        }
    }


    private class InternalDecoder extends SimpleChannelUpstreamHandler {
    }
}
