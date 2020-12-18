package edu.rutgers.cs552.im.client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

    // the timeout for heartbeat
    private static final Integer HeartBeatTimeOut = 60;


    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Autowired
    private NettyToFrontEndHandler nettyToFrontEndHandler;

    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline()
                // idle
                .addLast(new IdleStateHandler(HeartBeatTimeOut, 0, 0))
                .addLast(new ReadTimeoutHandler(3 * HeartBeatTimeOut))
                // encode
                .addLast(new MessageEncoder())
                // decode
                .addLast(new MessageDecoder())
                // redirect to webSocket
                .addLast(nettyToFrontEndHandler)
                // main client logic
                .addLast(nettyClientHandler)
        ;
    }

}
