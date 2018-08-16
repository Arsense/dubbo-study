package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * @author tangwei
 * @date 2018/8/16 10:10
 */
public class NettyServerHandler extends SimpleChannelInboundHandler{



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        //接收处理数据 ByteBuf
        ByteBuf buffer  = (ByteBuf) message;
        byte[] request = new byte[buffer.readableBytes()];
        buffer.readBytes(request);

        String msg = new String(request);
        System.out.println("receive data from client:" + msg);

        ByteBuf response = Unpooled.copiedBuffer(msg.getBytes());
        channelHandlerContext.write(response);
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
