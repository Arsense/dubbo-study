package com.remote.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**netty编码
 * @author tangwei
 * @date 2018/8/30 18:39
 */
public class NettyDecodeHandler extends ByteToMessageDecoder {

    //解码对象class
    private Class<?> genericClass;

    NettyDecodeHandler(Class<?> genericClass){
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {


    }
}
