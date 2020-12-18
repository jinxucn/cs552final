package edu.rutgers.cs552.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    // the timeout for heartbeat
    private static final Integer HeartBeatTimeOut = 3 * 60;

    // @Autowired
    // private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Autowired
    private NettyToDaoHandler nettyToDaoHandler;

    @Override
    protected void initChannel(Channel ch) {
        System.out.println("-----------------------SUCCESS!!!!!!!!-----------------------");
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline
                // idle
                .addLast(new ReadTimeoutHandler(HeartBeatTimeOut, TimeUnit.SECONDS))
                // encode
                .addLast(new MessageEncoder())
                // decode
                .addLast(new MessageDecoder())
                // msg to Dao 
                .addLast(nettyToDaoHandler)
                // main client logic
                .addLast(nettyServerHandler)
        ;
    }

}
