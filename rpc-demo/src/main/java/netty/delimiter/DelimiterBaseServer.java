package netty.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author tangwei
 * @date 2018/8/16 14:50
 */
public class DelimiterBaseServer {

    private final static String delimiterTag = "@#";


    public static void main(String[] args) {
        new DelimiterBaseServer().bind(8888);
    }

    /**
     * Netty服务启动函数
     * @param port
     */
    private void bind(int port){

        //创建两个专门处理网络事件的线程组
        EventLoopGroup adminGroups = new NioEventLoopGroup();
        EventLoopGroup  guestGroups = new NioEventLoopGroup();

        ServerBootstrap bootstrapServer = new ServerBootstrap();
        try {
            //设置NIO线程组 channel
            bootstrapServer.group(adminGroups,guestGroups)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //设置I/O事件处理类 用来处理消息
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            //设置解码处理器
                            //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(delimiterTag.getBytes())));
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(delimiterTag.getBytes())));

                            //设置StringDecoder处理器
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new DelimilterBaseServerHandler());
                        }
                    });
            //绑定端口 等待同步

            ChannelFuture channelFuture = bootstrapServer.bind(port).sync();
            //等待服务监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            adminGroups.shutdownGracefully();
            guestGroups.shutdownGracefully();
        }

        //

    }
}
