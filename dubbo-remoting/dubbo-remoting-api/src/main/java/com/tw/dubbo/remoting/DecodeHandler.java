package com.tw.dubbo.remoting;

import com.tw.dubbo.remoting.channel.ChannelHandler;

/**
 * @author tangwei
 * @date 2018/12/3 11:31
 */
public class DecodeHandler implements ChannelHandler {

    protected ChannelHandler handler;

    public DecodeHandler(ChannelHandler handler) {
        this.handler = handler;
    }
}
