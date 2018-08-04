package league.of.top;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author tangwei
 * @date 2018/8/4 19:18
 */
public class NettyDecodeHandler  extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext
            , ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
