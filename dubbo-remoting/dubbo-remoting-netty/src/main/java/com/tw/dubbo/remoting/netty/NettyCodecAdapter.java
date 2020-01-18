package com.tw.dubbo.remoting.netty;

import com.tw.dubbo.remoting.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author tangwei
 * @date 2018/12/5 12:20
 */
public class NettyCodecAdapter {
    private final ChannelHandler encoder = (ChannelHandler) new InternalEncoder();

    private final ChannelHandler decoder = (ChannelHandler) new InternalDecoder();

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }



    private class InternalEncoder extends OneToOneEncoder {

    }


    private class InternalDecoder extends SimpleChannelUpstreamHandler {
    }
}
