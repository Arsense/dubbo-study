package com.tw.dubbo.remoting.header;

import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.*;
import com.tw.dubbo.remoting.exception.RemotingException;
import com.tw.dubbo.remoting.exchange.ExchangeHandler;
import com.tw.dubbo.remoting.exchange.ExchangeServer;
import com.tw.dubbo.remoting.exchange.Exchanger;

/**
 * 默认的Exchanger
 *
 *
 * @author tangwei
 * @date 2018/12/3 11:17
 */
public class HeaderExchanger implements Exchanger {
    @Override
    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }
}
