package com.tw.dubbo.remoting.header;

import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exchange.ExchangeHandler;

/**
 * @author tangwei
 * @date 2018/12/3 11:30
 */
public class HeaderExchangeHandler implements ChannelHandler {

    private  ExchangeHandler handler;

    public HeaderExchangeHandler(ExchangeHandler handler) {
        this.handler = handler;
    }
}
