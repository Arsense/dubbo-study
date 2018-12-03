package com.tw.dubbo.remoting;

import com.tw.dubbo.common.util.URL;

/**
 * @author tangwei
 * @date 2018/12/3 10:56
 */
public interface Exchanger {

    ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException;

}
