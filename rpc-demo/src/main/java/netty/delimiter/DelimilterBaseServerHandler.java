package netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwei
 * @date 2018/8/17 18:04
 */
public class DelimilterBaseServerHandler extends SimpleChannelInboundHandler {


    private final static String delimiterTag = "@#";


    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        System.out.println("server channelRead0 in");
        //接收客户端发送的字符串 并打印到控制台
        String content = (String) message;
        System.out.println("recevice data from client" + content
                        +"counter:" + counter.addAndGet(1));
        //加入分隔符 将数据重新发送到客户端
        content += delimiterTag;
        ByteBuf echo = Unpooled.copiedBuffer(content.getBytes());
        channelHandlerContext.writeAndFlush(echo);
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
