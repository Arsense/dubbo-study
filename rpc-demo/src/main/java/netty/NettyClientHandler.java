package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwei
 * @date 2018/8/16 10:57
 */
public class NettyClientHandler extends SimpleChannelInboundHandler {

    private static final AtomicInteger counter = new AtomicInteger(0);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
//        ByteBuf buffer = (ByteBuf) message;
//        byte[] request = new byte[buffer.readableBytes()];
//        buffer.readBytes(request);

        System.out.println("receive data from server:" + message+ counter.addAndGet(1));

    }



    /**
     * 异常处理函数
     * @param channelHandlerContext
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
    }
}
