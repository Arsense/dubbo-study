package com.remote.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author tangwei
 * @date 2018/8/30 18:39
 */
public class NettyEncodeHandler extends MessageToByteEncoder {

    //解码对象class
    private Class<?> genericClass;

    NettyEncodeHandler(Class<?> genericClass ){
        this.genericClass = genericClass;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

    }

    public Class<?> getGenericClass() {
        return genericClass;
    }

    public void setGenericClass(Class<?> genericClass) {
        this.genericClass = genericClass;
    }
}
