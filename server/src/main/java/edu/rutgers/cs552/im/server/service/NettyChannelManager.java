package edu.rutgers.cs552.im.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.rutgers.cs552.im.server.dao.OfflineDOMapper;
import edu.rutgers.cs552.im.server.dataobject.OfflineDO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * manage the channel to different clients
 * send msg to specific client
 */
@Component
public class NettyChannelManager {

    private static final AttributeKey<String> UserAttribute = AttributeKey.newInstance("user");

    private Logger logger = LoggerFactory.getLogger(getClass());

    // two mapping, channels for all connection, users for users
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Channel> userChannels = new ConcurrentHashMap<>();

    @Autowired
    private OfflineDOMapper offlineDOMapper;

    public void add(Channel channel) {
        channels.put(channel.id(), channel);
        logger.info("[add][channel ({})]", channel.id());
    }

    public void addUser(Channel channel, String user) {
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null) {
            logger.error("[addUser][channel({}) is not exist]", channel.id());
            return;
        }
        channel.attr(UserAttribute).set(user);
        // add to users
        userChannels.put(user, channel);
    }

    public void remove(Channel channel) {
        // remove channels
        channels.remove(channel.id());
        // if is is also in users, then remove too
        if (channel.hasAttr(UserAttribute)) {
            userChannels.remove(channel.attr(UserAttribute).get());
        }
        logger.info("[remove][channel({})close]", channel.id());
    }


    public void send(String user, String msg) {
        // send msg to user 
        Channel channel = userChannels.get(user);
        if (channel == null || !channel.isActive()) {
            logger.error("[send][连接不存在]");
            JSONObject message = JSON.parseObject(msg);
            String fromId = message.getString("from");
            OfflineDO offlineDO = new OfflineDO();
            offlineDO.setFromID(fromId);
            offlineDO.setToID(user);
            offlineDO.setMessage(message.getString("message"));
            offlineDO.setIs_sent(0);
            offlineDOMapper.insert(offlineDO);
            return;
        }
        channel.writeAndFlush(msg);
    }

    public boolean isActive(String user){
        return userChannels.containsKey(user)&&userChannels.get(user).isActive();
    }

}
