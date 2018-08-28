package netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwei
 * @date 2018/8/20 9:27
 */
public class DelimilterBaseClientHandler  extends SimpleChannelInboundHandler {



    private static final AtomicInteger counter = new AtomicInteger(0);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        ByteBuf buffer = (ByteBuf) message;
        byte[] request = new byte[buffer.readableBytes()];
        buffer.readBytes(request);

        System.out.println("receive data from server:" + Arrays.toString(request) + counter.addAndGet(1));
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

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.flush();
    }
}
