package com.tw.dubbo.remoting.exchange;

import com.tw.dubbo.remoting.exception.RemotingException;
import com.tw.dubbo.remoting.channel.Channel;
import com.tw.dubbo.remoting.channel.ChannelHandler;

/**
 * @author clay
 * @date 2018/12/3 10:47
 */
public interface ExchangeHandler extends ChannelHandler {


    /**
     * 建立连接
     * @param channel
     * @throws RemotingException
     */
    void connected(Channel channel) throws RemotingException;

    /**
     * channel 断开连接
     *
     * @param channel channel.
     */
    void disconnected(Channel channel) throws RemotingException;

    /**
     * 发送消息
     *
     * @param channel channel.
     * @param message message.
     */
    void sent(Channel channel, Object message) throws RemotingException;

    /**
     * 接收消息.
     *
     * @param channel channel.
     * @param message message.
     */
    void received(Channel channel, Object message) throws RemotingException;
}
