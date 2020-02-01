package com.tw.dubbo.rpc.protocol;

import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.rpc.*;

/**
 * 协议发布的抽象类
 *
 *
 * @author clay
 * @date 2018/12/3 9:41
 */
public class AbstractProtocol implements Protocol {
    @Override
    public int getDefaultPort() {
        return 0;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }


    protected static String serviceKey(URL url) {
        int port = url.getPort();
        return ProtocolUtils.serviceKey(port, url.getPath(), url.getParameter("version"), url.getParameter("group"));
    }
}
