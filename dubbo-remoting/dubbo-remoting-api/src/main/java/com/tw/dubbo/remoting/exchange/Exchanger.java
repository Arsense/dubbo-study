package com.tw.dubbo.remoting.exchange;

import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author clay
 * @date 2018/12/3 10:56
 */
public interface Exchanger {

    //消费者端 建立连接
//    ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException;

    ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException;

}
