package edu.rutgers.cs552.im.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // mark Current readableBytes
        in.markReaderIndex();
        // if the length is readable
        if (in.readableBytes() <= 4) {
            return;
        }
        // read length
        int length = in.readInt();
        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }
        // if length unreadable, go back to the beginning places
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        // read content
        byte[] content = new byte[length];
        in.readBytes(content);
        // stringify
        String msg = new String(content);
        out.add(msg);
        logger.info("[decode][channel({}) decode({})]", ctx.channel().id(), msg);
    }
}
