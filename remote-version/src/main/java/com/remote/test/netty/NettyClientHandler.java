package com.remote.test.netty;

import com.remote.test.utils.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangwei
 * @date 2018/9/5 14:17
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response>{
    private final static Logger LOG = LoggerFactory.getLogger(NettyClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        //将Netty异步返回的结果存入阻塞队列,以便调用端同步获取
        System.out.println("Client channelRead0 when read  . channel ===>" + channelHandlerContext.channel());//15个channel会被重复使用

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
