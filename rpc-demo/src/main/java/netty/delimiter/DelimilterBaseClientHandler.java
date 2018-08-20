package netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tangwei
 * @date 2018/8/20 9:27
 */
public class DelimilterBaseClientHandler  extends SimpleChannelInboundHandler {


    private final static String delimiterTag = "@#";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        System.out.println("client channelRead0 in");
        ByteBuf buffer = (ByteBuf) message;
        byte[] request = new byte[buffer.readableBytes()];
        buffer.readBytes(request);

        System.out.println("receive data from server:" +  new String(request));

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
