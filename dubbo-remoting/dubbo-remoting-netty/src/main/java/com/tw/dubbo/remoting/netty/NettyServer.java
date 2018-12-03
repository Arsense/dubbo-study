package com.tw.dubbo.remoting.netty;

import com.tw.dubbo.common.NamedThreadFactory;
import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.AbstractServer;
import com.tw.dubbo.remoting.Server;
import com.tw.dubbo.remoting.channel.Channel;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tangwei
 * @date 2018/12/3 12:15
 */
public class NettyServer extends AbstractServer implements Server {

    private ServerBootstrap bootstrap;

    private URL url;




    @Override
    protected void doOpen() throws Throwable {
        //创建BOSS 和Worker 这样的netty调用方法之前没用过
        ExecutorService boss = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerBoss", true));

        ExecutorService worker = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerWorker", true));

        ChannelFactory channelFactory = new NioServerSocketChannelFactory(boss, worker, url.getPositiveParameter("iothreads", Math.min(Runtime.getRuntime().availableProcessors() + 1, 32)));
        //这里建立连接
        bootstrap = new ServerBootstrap(channelFactory);

        final NettyHandler nettyHandler = new NettyHandler(url, this);
        channels = nettyHandler.getChannels();
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            //添加配置Handler和解码器
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                //
                NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), getUrl(), NettyServer.this);
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", adapter.getDecoder());
                pipeline.addLast("encoder", adapter.getEncoder());
                pipeline.addLast("handler", nettyHandler);
                return pipeline;
            }
        });
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
