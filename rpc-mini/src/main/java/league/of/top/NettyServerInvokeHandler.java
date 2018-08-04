package league.of.top;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tangwei
 * @date 2018/8/4 19:19
 */
public class NettyServerInvokeHandler  extends SimpleChannelInboundHandler<SkillRequest> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SkillRequest skillRequest) throws Exception {

    }
}
