package com.tw.dubbo.rpc.protocol.dubbo;

import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.ExchangeServer;
import com.tw.dubbo.remoting.Exchangers;
import com.tw.dubbo.remoting.Transporter;
import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Invoker;
import com.tw.dubbo.rpc.RpcException;
import com.tw.dubbo.rpc.protocol.AbstractProtocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangwei
 * @date 2018/11/30 15:08
 */
public class DubboProtocol extends AbstractProtocol {

    //server存放map
    private final Map<String, ExchangeServer> serverMap = new ConcurrentHashMap<>();

    @Override
    public int getDefaultPort() {
        return 0;
    }

    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();
    /**
     * 发布相关的service
     * @param invoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        //先放到相应的Map里面
        URL url = invoker.getUrl();
        // export service.
        //格式的 key = 集群名+服务类型+服务版本+端口
        String key = serviceKey(url);
        DubboExporter<T> exporter = new DubboExporter<T>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);
        openServer(url);

        return null;
    }

    /**
     * 寻找相应的server
     * @param url
     */
    private void openServer(URL url) {
        String key = url.getAddress();
        //客户端可以导出仅供服务器调用的服务
        boolean isServer = Boolean.parseBoolean(url.getParameter("isserver"));
        if (isServer) {
            ExchangeServer server = serverMap.get(key);
            if (server == null) {
                synchronized (this) {
                    server = serverMap.get(key);
                    if (server == null) {
                        serverMap.put(key, createServer(url));
                    }
                }
            } else {
                // server supports reset, use together with override
//                server.reset(url);
            }
        }
    }

    /**
     * 得到交换中心
     * @param url
     * @return
     */
    private ExchangeServer createServer(URL url) {
        //在服务器关闭时发送readonly事件，默认情况下启用它
        //还是用的netty最为socket之间的传输啊
        String str = url.getParameter("server", "netty");
        //搜偶socket传输方式放到
        if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)){
            throw new RpcException("Unsupported server type: " + str + ", url: " + url);
        }

        ExchangeServer server;
        try {
            //开始绑定准备传输  了
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }

    }

        @Override
    public URL getUrl() {
        return null;
    }
}
