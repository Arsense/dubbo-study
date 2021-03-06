package com.tw.dubbo.remoting;

import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.remoting.channel.ChannelHandler;
import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author clay
 * @date 2018/12/3 10:33
 */
public interface Transporter {

    Server bind(URL url, ChannelHandler handler) throws RemotingException;

}
