package netty.delimiter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwei
 * @date 2018/8/20 9:27
 */
public class DelimilterBaseClientHandler  extends SimpleChannelInboundHandler {


    private final static String delimiterTag = "@#";

    private static final AtomicInteger counter = new AtomicInteger(0);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        String context = (String) message;

        System.out.println("receive data from server:" + message + counter.addAndGet(1));
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
