package com.tw.dubbo.remoting.exchange;

import com.tw.dubbo.remoting.exception.RemotingException;
import com.tw.dubbo.remoting.channel.Channel;

/**
 * @author clay
 * @date 2018/12/3 11:06
 */
public class ExchangeHandlerAdapter implements ExchangeHandler {
    @Override
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }
}
