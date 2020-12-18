/*
 * @Author: Jin X
 * @Date: 2020-12-11 21:06:20
 * @LastEditTime: 2020-12-11 22:49:42
 */
package edu.rutgers.cs552.im.server.handler;

// import edu.rutgers.cs552.im.common.codec.InvocationDecoder;
// import edu.rutgers.cs552.im.common.codec.InvocationEncoder;
// import edu.rutgers.cs552.im.common.dispatcher.MessageDispatcher;
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
    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    // @Autowired
    // private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Autowired
    private NettyToDaoHandler nettyToDaoHandler;

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline
                // idle
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
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
