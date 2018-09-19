package com.remote.test.netty;

import com.remote.test.utils.Request;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author tangwei
 * @date 2018/8/30 18:08
 */
public class NettyServer {


    private  static NettyServer nettyServer = new NettyServer();
    public static NettyServer singleton() {
        return nettyServer;
    }
    private Channel channel;

    //服务端boss线程组
    private EventLoopGroup bossGroup;
    //服务端worker线程组
    private EventLoopGroup workerGroup;


    public synchronized void start(final int port){
        //防止重复连
        if (bossGroup != null || workerGroup != null) {
            return;
        }
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //添加netty日志 方便调试
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                        //三个 序列化编码和解码 处理函数 不然消息收发不到
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                        socketChannel.pipeline().addLast(new NettyDecodeHandler(Request.class));
                        socketChannel.pipeline().addLast();
                    }
                    //配置接收处理消息的Handler
                });
        try {
            channel = serverBootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
