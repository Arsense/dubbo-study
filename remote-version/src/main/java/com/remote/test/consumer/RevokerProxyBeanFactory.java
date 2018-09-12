package com.remote.test.consumer;

import com.remote.test.cluster.ClusterChooseService;
import com.remote.test.cluster.ClusterStrategy;
import com.remote.test.provider.ProviderService;
import com.remote.test.utils.Request;
import com.remote.test.utils.Response;
import com.remote.test.zookeeper.RegisterCenter;

import javax.print.DocFlavor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author tangwei
 * @date 2018/9/11 19:44
 */
public class RevokerProxyBeanFactory implements InvocationHandler {

    private Class<?> targetInterface;
    //超时时间
    private int consumeTimeout = 1000;
    //调用者线程数
    private static int threadWorkerNumber = 10;
    //负载均衡策略
    private String clusterStrategy = "WeightRandom";

    private ExecutorService fixedThreadPool = null;

    /**
     * 实现代理
     * @return
     */
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{targetInterface}, this);
    }
    /**
     * 负载均衡先不管
     * 构造函数
     */
    RevokerProxyBeanFactory(Class<?> targetInterface , String clusterStrategy ){
        this.targetInterface = targetInterface;
    }


    /**
     * 调用服务方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       //根据接口名获取服务提供者列表 然后 负载均衡知道本次的方法和接口 发起调用该
        String interfaceName = targetInterface.getName();

        RegisterCenter registerCenter = RegisterCenter.singleton();
        //获取接口列表
        List<ProviderService> providerServices = registerCenter.getProviderServicesToConsume().get(interfaceName);
        //根据软负载策略,从服务提供者列表选取本次调用的服务提供者
        ClusterStrategy strategy = ClusterChooseService.matchClusterStrategy(clusterStrategy);
        ProviderService providerService = strategy.select(providerServices);
        //复制一份服务提供者信息
        ProviderService newProvider = providerService.copy();
        newProvider.setServiceMethod(method);
        newProvider.setServiceInterface(targetInterface);

        final Request request  = new Request();
        request.setProviderService(providerService);
        request.setTimeout(consumeTimeout);
        request.setInvokeMethodName(method.getName());
        //传递参数
        request.setArgs(args);
        //设置本次调用方法信息 然后这个放送给netty 远程开始调用
        try {
            //构建用来发起调用的线程池
            if (fixedThreadPool == null) {
                synchronized (RevokerProxyBeanFactory.class) {
                    if (null == fixedThreadPool) {
                        fixedThreadPool = Executors.newFixedThreadPool(threadWorkerNumber);
                    }
                }
            }
            //根据服务提供者的ip,port,构建InetSocketAddress对象,标识服务提供者地址
            String serverIp = request.getProviderService().getServerIp();
            String serverPort = request.getProviderService().getPort();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(serverIp, Integer.parseInt(serverPort));
            //提交本次调用信息到线程池fixedThreadPool,发起调用
            //Future表示一个可能还没有完成的异步任务的结果，针对这个结果可以添加Callback以便在任务执行成功或失败后作出相应的操作。
            //做多等待timeout的时间就会返回结果
            Future<Response> responseFuture = fixedThreadPool.submit(RevokerServiceCallable.of(inetSocketAddress , request));
            Response response = responseFuture.get(request.getTimeout() , TimeUnit.MILLISECONDS);
            if(response != null) {
                return response.getResult();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("invoke 调用失败");
        }

        return  null;

    }



}
