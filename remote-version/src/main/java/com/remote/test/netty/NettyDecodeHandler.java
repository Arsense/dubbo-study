package com.remote.test.netty;

import com.remote.test.serialize.JsonSerializer;
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
        //获取消息头所标识的消息体字节数组长度
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (dataLength < 0) {
            channelHandlerContext.close();
        }
        //若当前可以获取到的字节数小于实际长度,则直接返回,直到当前可以获取到的字节数等于实际长度
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        //读取完整的消息体字节数组
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        JsonSerializer jsonSerializer = new JsonSerializer();
        //将字节数组反序列化为java对象(SerializerEngine参考序列化与反序列化章节)
        Object object = jsonSerializer.deserialize(data, genericClass);

        list.add(object);
    }
}
