package edu.rutgers.cs552.im.client.handler;

import edu.rutgers.cs552.im.client.service.NettyClient;
// import edu.rutgers.cs552.im.client.message.heartbeat.HeartbeatRequest;
// import edu.rutgers.cs552.im.common.codec.Invocation;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyClient nettyClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 发起重连
        nettyClient.reconnect();
        // 继续触发事件
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("[exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
        // 断开连接
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        // 空闲时，向服务端发起一次心跳
        if (event instanceof IdleStateEvent) {
            logger.info("[userEventTriggered][发起一次心跳]");
            JSONObject request = new JSONObject();
            request.put("type", 0);
            ctx.writeAndFlush(request.toJSONString());
                    // .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, event);
        }
    }

}
