package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author tangwei
 * @date 2018/8/16 10:57
 */
public class NettyClient {


    public static void main(String[] args) {
        new NettyClient().connectServer("127.0.0.1",8081);
    }
    public void connectServer(String host , int port) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            //设置NIO线程组
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<NioSocketChannel>(){
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            //配置客户端处理网络IO事件的类
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(host,port).sync();

            byte[] request = "你好 Server Netty".getBytes();
            ByteBuf buffer = Unpooled.buffer(request.length);
            buffer.writeBytes(request);

            ChannelFuture channelFuture = future.channel().writeAndFlush(buffer);
            channelFuture.syncUninterruptibly();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientGroup.shutdownGracefully();
        }


    }
}
