/*
 * @Author: Jin X
 * @Date: 2020-12-11 11:49:29
 * @LastEditTime: 2020-12-11 22:42:49
 */
package edu.rutgers.cs552.im.client.handler;

// import edu.rutgers.cs552.im.common.codec.InvocationDecoder;
// import edu.rutgers.cs552.im.common.codec.InvocationEncoder;
// import edu.rutgers.cs552.im.common.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

    // the timeout for heartbeat
    private static final Integer READ_TIMEOUT_SECONDS = 60;

    // @Autowired
    // private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Autowired
    private NettyToFrontEndHandler nettyToFrontEndHandler;

    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline()
                // idle
                .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(3 * READ_TIMEOUT_SECONDS))
                // encode
                .addLast(new MessageEncoder())
                // decode
                .addLast(new MessageDecoder())
                // .addLast(messageDispatcher)
                // redirect to webSocket
                .addLast(nettyToFrontEndHandler)
                // main client logic
                .addLast(nettyClientHandler)
        ;
    }

}
