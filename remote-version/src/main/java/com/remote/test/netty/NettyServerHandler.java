package com.remote.test.netty;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.remote.test.service.ProviderService;
import com.remote.test.utils.Request;
import com.remote.test.utils.Response;
import com.remote.test.zookeeper.RegisterCenter;
import com.remote.test.zookeeper.ServiceRegistryCenter;

import java.util.List;


/**
 * @author tangwei
 * @date 2018/8/30 18:40
 */
public class NettyServerHandler  extends SimpleChannelInboundHandler<Request>{

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerHandler.class);

//    //服务端限流
//    private static final Map<String, Semaphore> serviceKeySemaphoreMap = Maps.newConcurrentMap();

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) {
        LOG.info("server 收到信息");
        if( channelHandlerContext.channel().isWritable()) {
            //从服务器调用对象里获取服务提供者信息
            //先设置好线程数  线程先不会怎么样
            ProviderService providerService = request.getProviderService();
            String serviceKey = providerService.getServiceInterface().getName();
            String methodName = request.getInvokeMethodName();
//            //信号量控制线程数
//            Semaphore semaphore = serviceKeySemaphoreMap.get(serviceKey);
//
//            if (semaphore == null) {
//                //先不加锁 看会怎么样
//                semaphore = new Semaphore(20);
//                serviceKeySemaphoreMap.put(serviceKe`y,semaphore);
//            }

            //获取注册服务中心

            ServiceRegistryCenter serverRegisterCenter = RegisterCenter.singleton();
            List<ProviderService> localProviderCaches = serverRegisterCenter.getRegisterProviderMap().get(serviceKey);
            ProviderService localProvider = new ProviderService();
            for(ProviderService localProviderCache : localProviderCaches) {
                if ( localProviderCache.getServiceMethod().getName().equals(methodName)){
                    localProvider = localProviderCache;
                }
            }

            //创建服务返回对象
            Response response = new Response();
            response.setResult(localProvider);
            response.setInvokeTimeout(request.getTimeout());
            //这个相当于身份识别
            response.setUniqueKey(request.getUniqueKey());

            //将服务调用返回对象回写到消费端
            System.out.println("when write ,channel ===> " + channelHandlerContext.channel());
            channelHandlerContext.writeAndFlush(response);
        } else {
            LOG.error(" channel closed");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


}
