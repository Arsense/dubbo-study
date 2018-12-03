package com.tw.dubbo.remoting.exchange;


import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author tangwei
 * @date 2018/12/3 10:45
 */
public class Exchangers {

    public static ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        return getExchanger(url).bind(url, handler);
    }



    public static Exchanger getExchanger(URL url) {
        String type = url.getParameter("exchanger", "netty");
        return ExtensionLoader.getExtensionLoader(Exchanger.class).getExtension(type);
    }
}


