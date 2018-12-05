package com.tw.dubbo.remoting.netty;

import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import org.jboss.netty.channel.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Netty具体的处理类
 *
 * @author tangwei
 * @date 2018/12/3 20:29
 */
public class NettyHandler extends SimpleChannelHandler {

    private final URL url;

    private final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>(); // <ip:port, channel>

    public Map<String, Channel> getChannels() {
        return channels;
    }

    /**
     * 接收到消息
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
    }


    /**
     * 建立上连接
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.writeRequested(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
    }

    private final ChannelHandler handler = null;

    public NettyHandler(URL url, NettyServer nettyServer) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.url = url;
//        this.handler = handler;
    }
}
