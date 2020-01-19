package com.tw.dubbo.remoting;

import com.tw.dubbo.remoting.channel.Channel;

import java.util.Collection;

/**
 * @author clay
 * @date 2018/12/3 10:14
 */
public interface Server {


    /**
     * 是否绑定.
     *
     * @return bound
     */
    boolean isBound();

    /**
     * 得到channels. 自定义channel
     *
     * @return channels
     */
    Collection<Channel> getChannels();
}
