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

    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    // @Autowired
    // private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Autowired
    private NettyToDaoHandler nettyToDaoHandler;

    @Override
    protected void initChannel(Channel ch) {
        System.out.println("-----------------------SUCCESS!!!!!!!!-----------------------");
        // 获得 Channel 对应的 ChannelPipeline
        ChannelPipeline channelPipeline = ch.pipeline();
        // 添加一堆 NettyServerHandler 到 ChannelPipeline 中
        channelPipeline
                // 空闲检测
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                // 编码器
                .addLast(new MessageEncoder())
                // 解码器
                .addLast(new MessageDecoder())
                // 消息分发器
                // .addLast(messageDispatcher)
                // 后端Dao接口
                .addLast(nettyToDaoHandler)
                // 服务端处理器
                .addLast(nettyServerHandler)
        ;
    }

}
