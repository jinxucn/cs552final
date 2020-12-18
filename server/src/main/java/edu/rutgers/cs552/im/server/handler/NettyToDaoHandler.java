package edu.rutgers.cs552.im.server.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * The dao adapter 
*/
@Component
@ChannelHandler.Sharable
public class NettyToDaoHandler extends SimpleChannelInboundHandler<String> {

    
    @Autowired    
    private MessageHandler handler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        
        handler.handleMessage(ctx.channel(), msg);
    }
}
