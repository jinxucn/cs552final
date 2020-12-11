/*
 * @Author: Jin X
 * @Date: 2020-12-11 22:45:28
 * @LastEditTime: 2020-12-11 22:48:43
 */
package edu.rutgers.cs552.im.server.handler;

import edu.rutgers.cs552.im.server.dao.Dao;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
@ChannelHandler.Sharable
public class NettyToDaoHandler extends ChannelInboundHandlerAdapter {

    
    @Autowired    
    private Dao dao;

    @Override
    public void channelRead(ChannelHandlerContext ctx, String msg){
        
        dao.handleMessage(msg);
    }
}
