package com.remote.test.netty;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.remote.test.provider.ProviderService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/** 消费者Netty队列
 * @author tangwei
 * @date 2018/9/5 13:41
 */
public class NettyCosumeChannelQueue {
    private static final Logger LOG = LoggerFactory.getLogger(NettyCosumeChannelQueue.class);

    private static final NettyCosumeChannelQueue channelPoolFactory = new NettyCosumeChannelQueue();

    //Key为服务提供者地址,value为Netty Channel阻塞队列
    private static final Map<InetSocketAddress, ArrayBlockingQueue<Channel>> channelQueueMap
            = Maps.newConcurrentMap();
    //服务提供者列表
    private List<ProviderService> providerServicesList = Lists.newArrayList();

    public static NettyCosumeChannelQueue singleton(){
        return channelPoolFactory;
    }

    /**
     * 初始化Netty channel 连接队列Map
     * @param providerMap
     */
    public void initChannelPoolFactory(Map<String, List<ProviderService>> providerMap) throws Exception {
        //将服务提供者信息存入serviceMetaDataList列表
        Collection<List<ProviderService>> collectionServiceMetaDataList = providerMap.values();
        for (List<ProviderService> serviceMetaDataModels : collectionServiceMetaDataList) {
            if (CollectionUtils.isEmpty(serviceMetaDataModels)) {
                continue;
            }
            providerServicesList.addAll(serviceMetaDataModels);
        }

        //获取服务提供者地址列表
        for (ProviderService providerService : providerServicesList) {
            InetSocketAddress socketAddress = new InetSocketAddress(providerService.getServerIp()
                    , Integer.parseInt(providerService.getPort()));
            try {
                int channelConnetionCount = 0;
                //最多先设置连接20个
                while(channelConnetionCount < 20) {
                    Channel channel = null;
                    while (channel == null) {
                        channel = registerChannel(socketAddress);
                    }
                    channelConnetionCount++;
                    //将新注册的Netty Channel存入阻塞队列channelArrayBlockingQueue
                    // 并将阻塞队列channelArrayBlockingQueue作为value存入channelPoolMap
                    ArrayBlockingQueue<Channel> channelArrayBlockingQueue = channelQueueMap.get(socketAddress);

                    if (channelArrayBlockingQueue == null) {
                        channelArrayBlockingQueue = new ArrayBlockingQueue<Channel>(20);
                        channelQueueMap.put(socketAddress , channelArrayBlockingQueue);
                    }
                    //offer 尾插法
                    channelArrayBlockingQueue.offer(channel);
                }

            } catch (Exception e) {
                throw new RuntimeException("socket Error");
            }

        }

    }


    public Channel registerChannel(InetSocketAddress socketAddress) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup(10);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.remoteAddress(socketAddress);

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //注册客户端业务逻辑处理handler
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();

    }
}
