package edu.rutgers.cs552.im.server.messagehandler.chat;

import edu.rutgers.cs552.im.common.codec.Invocation;
import edu.rutgers.cs552.im.common.dispatcher.MessageHandler;
import edu.rutgers.cs552.im.server.message.chat.ChatSendResponse;
import edu.rutgers.cs552.im.server.message.chat.ChatSendToAllRequest;
import edu.rutgers.cs552.im.server.message.chat.ChatRedirectToUserRequest;
import edu.rutgers.cs552.im.server.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatSendToAllHandler implements MessageHandler<ChatSendToAllRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendToAllRequest message) {
        // 这里，假装直接成功
        ChatSendResponse sendResponse = new ChatSendResponse().setMsgId(message.getMsgId()).setCode(0);
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse));

        // 创建转发的消息，并广播发送
        ChatRedirectToUserRequest sendToUserRequest = new ChatRedirectToUserRequest().setMsgId(message.getMsgId())
                .setContent(message.getContent());
        nettyChannelManager.sendAll(new Invocation(ChatRedirectToUserRequest.TYPE, sendToUserRequest));
    }

    @Override
    public String getType() {
        return ChatSendToAllRequest.TYPE;
    }

}
