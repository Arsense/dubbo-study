package com.tw.dubbo.remoting;

import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;

import java.net.InetSocketAddress;

/**
 * @author clay
 * @date 2018/12/3 12:20
 */
public abstract class AbstractServer {


    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;
    public AbstractServer(URL url, ChannelHandler handler) throws RemotingException {
        localAddress = url.toInetSocketAddress();
        String bindIp = url.getParameter("bind.ip", url.getHost());
        int bindPort = url.getParameter("bind.port", url.getPort());
        bindAddress = new InetSocketAddress(bindIp, bindPort);
        try {
            doOpen();

        } catch (Throwable t) {
            throw new RemotingException("open error");
        }


        // fixme用更好的方法替换它
    }
}
