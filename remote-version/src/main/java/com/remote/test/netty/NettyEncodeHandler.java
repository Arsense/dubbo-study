package com.remote.test.netty;

import com.remote.test.serialize.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author tangwei
 * @date 2018/8/30 18:39
 */
public class NettyEncodeHandler extends MessageToByteEncoder {


    NettyEncodeHandler(){

    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        JsonSerializer serializer = new JsonSerializer();
        //将对象序列化为字节数组
        byte[] data = serializer.serialize(object);
        //将字节数组(消息体)的长度作为消息头写入,解决半包/粘包问题
        byteBuf.writeInt(data.length);
        //写入序列化后得到的字节数组
        byteBuf.writeBytes(data);
    }


}
