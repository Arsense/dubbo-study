package com.remote.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangwei
 * @date 2018/8/30 18:40
 */
public class NettyServerHandler  extends SimpleChannelInboundHandler{

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
