package league.of.top;


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

/** Netty 服务
 * @author tangwei
 * @date 2018/8/4 15:05
 */
public class NettyServer {


    private  static NettyServer  nettyServer = new NettyServer();
    private  Channel channel;
    private  EventLoopGroup adminGroup;
    private  EventLoopGroup commonGroup;

    public static NettyServer singleton() {
        return nettyServer;
    }


    /**
     * 启动Netty服务
     * @param port
     */
    public synchronized void  start(final int port) throws InterruptedException {
        if (adminGroup !=null || commonGroup != null) {
            return;
        }
        adminGroup = new NioEventLoopGroup();
        commonGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(adminGroup,commonGroup)
                       .channel(NioServerSocketChannel.class)
                       .option(ChannelOption.SO_BACKLOG,1024)
                       .childOption(ChannelOption.SO_KEEPALIVE,true)
                       .childOption(ChannelOption.TCP_NODELAY,true)
                       .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                //注册解码器NettyDecoderHandler
                                socketChannel.pipeline().addLast(new NettyDecodeHandler());
                                //注册编码器NettyEncoderHandler
                                socketChannel.pipeline().addLast(new NettyEncodeHandler());
                                //注册服务端业务逻辑处理器NettyServerInvokeHandler
                                socketChannel.pipeline().addLast(new NettyServerInvokeHandler());
                            }
                        });
        channel = serverBootstrap.bind(port).sync().channel();
    }

    /**
     * 停止netty服务
     */
    public void stop() {
        if (channel == null) {
           throw  new RuntimeException("Netty already stop");
        }
        adminGroup.shutdownGracefully();
        commonGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }

}
