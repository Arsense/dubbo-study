package com.tw.dubbo.remoting.header;

import com.tw.dubbo.remoting.channel.Channel;
import com.tw.dubbo.remoting.exchange.ExchangeChannel;
import com.tw.dubbo.remoting.exchange.ExchangeServer;
import com.tw.dubbo.remoting.Server;

import java.util.Collection;

/**
 * @author tangwei
 * @date 2018/12/3 11:20
 */
public class HeaderExchangeServer  implements ExchangeServer {

    /**
     * 构造初始化函数
     *
     * @param server
     */
    public HeaderExchangeServer(Server server) {
    }

        @Override
    public Collection<ExchangeChannel> getExchangeChannels() {
        return null;
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
