package edu.rutgers.cs552.im.client.handler;

import edu.rutgers.cs552.im.client.service.NettyClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

/**
 * handle when channel lost, reconnect or on error
 */
@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyClient nettyClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // reconnect link
        nettyClient.reconnect();
        // invoke the following channel handler
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("[exceptionCaught][link({}) error]", ctx.channel().id(), cause);
        // close link
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        // send a heartbeat when pipeline is empty
        if (event instanceof IdleStateEvent) {
            logger.info("[userEventTriggered][send a heartbeat]");
            JSONObject request = new JSONObject();
            request.put("type", 0);
            ctx.writeAndFlush(request.toJSONString());
        } else {
            super.userEventTriggered(ctx, event);
        }
    }

}
