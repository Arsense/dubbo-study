package com.tw.dubbo.remoting.netty;


import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.Server;
import com.tw.dubbo.remoting.Transporter;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author tangwei
 * @date 2018/12/3 12:06
 */
public class NettyTransporter  implements Transporter {


    @Override
    public Server bind(URL url, ChannelHandler listener) throws RemotingException {
        return new NettyServer(url, listener);
    }
}
