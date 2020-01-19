package com.tw.dubbo.remoting.exchange;

import com.tw.dubbo.remoting.Server;
import com.tw.dubbo.remoting.exchange.ExchangeChannel;

import java.util.Collection;

/**
 * @author clay
 * @date 2018/12/3 10:13
 */
public interface ExchangeServer  extends Server {

    /**
     * get channels.
     *
     * @return channels
     */
    Collection<ExchangeChannel> getExchangeChannels();
}
