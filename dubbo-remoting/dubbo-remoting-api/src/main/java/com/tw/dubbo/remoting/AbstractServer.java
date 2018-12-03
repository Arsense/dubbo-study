package com.tw.dubbo.remoting;

import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;

import java.net.InetSocketAddress;

/**
 * @author tangwei
 * @date 2018/12/3 12:20
 */
public class AbstractServer {


    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;
    public AbstractServer(URL url, ChannelHandler handler) throws RemotingException {
        localAddress = url.toInetSocketAddress();
    }
}
