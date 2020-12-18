package edu.rutgers.cs552.im.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageEncoder extends MessageToByteEncoder<String> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
        // get the content in bytes format
        byte[] content = msg.getBytes();
        // write length first
        out.writeInt(content.length);
        // then write the content
        out.writeBytes(content);
        logger.info("[encode][channel({}) encode({})]", ctx.channel().id(), msg);
    }

}




