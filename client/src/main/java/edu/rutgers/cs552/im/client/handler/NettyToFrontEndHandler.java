package edu.rutgers.cs552.im.client.handler;

import edu.rutgers.cs552.im.client.websocket.WebSocketInterface;


import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Component
@ChannelHandler.Sharable
public class NettyToFrontEndHandler extends SimpleChannelInboundHandler<String> {

    
    @Autowired    
    private WebSocketInterface webSocketInterface;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws IOException {
        
        webSocketInterface.sendMessage(msg);
    }
}
