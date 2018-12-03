package com.tw.dubbo.remoting;

import java.util.Collection;

/**
 * @author tangwei
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
