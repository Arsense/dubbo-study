package com.tw.dubbo.remoting;

import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.util.URL;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.channel.ChannelHandlerDispatcher;
import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author tangwei
 * @date 2018/12/3 11:21
 */
public class Transporters {
    //不定参数
    public static Server bind(URL url, ChannelHandler... handlers) throws RemotingException {

        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handlers == null || handlers.length == 0) {
            throw new IllegalArgumentException("handlers == null");
        }
        ChannelHandler handler;
        if (handlers.length == 1) {
            //一个handler
            handler = handlers[0];
        } else {
            handler = new ChannelHandlerDispatcher(handlers);
        }

        //绑定channel和 url 这里开始调用netty了
        return getTransporter().bind(url, handler);

    }


    public static Transporter getTransporter() {
        return ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
    }
}
