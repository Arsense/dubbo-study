package com.tw.dubbo.remoting.netty;

import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.AbstractServer;
import com.tw.dubbo.remoting.Server;
import com.tw.dubbo.remoting.Transporter;
import com.tw.dubbo.remoting.channel.Channel;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;

import java.util.Collection;

/**
 * @author tangwei
 * @date 2018/12/3 12:06
 */
public class NettyTransporter  extends AbstractServer implements Server {


    public NettyServer(URL url, ChannelHandler handler) throws RemotingException {
        super(url, ChannelHandlers.wrap(handler, ExecutorUtil.setThreadName(url, SERVER_THREAD_POOL_NAME)));
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
