package com.tw.dubbo.remoting.netty;

import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.remoting.AbstractServer;
import com.tw.dubbo.remoting.Server;
import com.tw.dubbo.remoting.channel.Channel;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Collection;
import java.util.Map;

/**
 * @author clay
 * @date 2018/12/3 12:15
 */
public class NettyServer extends AbstractServer implements Server {

    private ServerBootstrap bootstrap;

    private URL url;

    private Map<String, Channel> channels; // <ip:port, channel>

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    @Override
    protected void doOpen() throws Throwable {
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
        workerGroup = new NioEventLoopGroup(url.getPositiveParameter("iothreads", 20),
                new DefaultThreadFactory("NettyServerWorker", true));

        final NettyServerHandler nettyServerHandler = new NettyServerHandler(url, this);
        channels = nettyServerHandler.getChannels();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
//                        NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), url, NettyServer.this);
//                        ch.pipeline()//.addLast("logging",new LoggingHandler(LogLevel.INFO))//for debug
//                                .addLast("decoder", adapter.getDecoder())
//                                .addLast("encoder", adapter.getEncoder())
//                                .addLast("handler", nettyServerHandler);
                    }
                });
//        // bind
//        ChannelFuture channelFuture = bootstrap.bind(getBindAddress());
//        channelFuture.syncUninterruptibly();
//        channel = channelFuture.channel();
    }

    @Override
    protected void doClose() throws Throwable {


    }


    public NettyServer(URL url, ChannelHandler handler) throws RemotingException {
        //包装channel Handler
        super(url, handler);
        this.url = url;

    }


    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Collection<Channel> getChannels() {
        return null;
    }

}
