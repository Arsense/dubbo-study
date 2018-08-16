package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author tangwei
 * @date 2018/8/16 9:01
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(8081);
    }


    /**
     * Netty服务启动函数
     * @param port
     */
    private void bind(int port){

        //创建两个专门处理网络事件的线程组
        EventLoopGroup  adminGroups = new NioEventLoopGroup();
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
                        socketChannel.pipeline().addLast(new NettyServerHandler());
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
