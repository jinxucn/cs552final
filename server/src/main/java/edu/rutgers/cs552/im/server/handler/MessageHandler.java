package edu.rutgers.cs552.im.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.rutgers.cs552.im.server.dao.FriendDOMapper;
import edu.rutgers.cs552.im.server.dao.OfflineDOMapper;
import edu.rutgers.cs552.im.server.dao.UserDOMapper;
import edu.rutgers.cs552.im.server.dataobject.FriendDO;
import edu.rutgers.cs552.im.server.dataobject.OfflineDO;
import edu.rutgers.cs552.im.server.dataobject.UserDO;
import edu.rutgers.cs552.im.server.service.NettyChannelManager;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Component
public class MessageHandler{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Autowired
    private UserDOMapper userDoMapper;

    @Autowired
    private FriendDOMapper friendDOMapper;
    // provides
    // nettyChannelManager.send(user,msg);
    // nettyChannelManager.isActive(user);

    @Autowired
    private OfflineDOMapper offlineDOMapper;


    public void forwardOfflineMessage(String user){
        List<OfflineDO> messageList = offlineDOMapper.selectByToID(user);
        for(OfflineDO msg: messageList){
            JSONObject forward = new JSONObject();
            forward.put("type", 4);
            forward.put("from", msg.getFromID());
            forward.put("message", msg.getMessage());
            nettyChannelManager.send(msg.getToID(), forward.toJSONString());
        }
        offlineDOMapper.deleteByToId(user);
    }

    public void handleHeartbeat(Channel channel, JSONObject message){
        JSONObject response = new JSONObject();
        response.put("type", 0);
        channel.writeAndFlush(response.toJSONString());
    }


    public void handleAuthReq(Channel channel, JSONObject message){
        JSONObject response = new JSONObject();
        String userid = message.getString("userId");
        String password = message.getString("password");
        if (!userid.isEmpty()) {
            UserDO user = userDoMapper.selectByUserID(userid);
            if(user != null && user.getPassword().equals(password)){
                nettyChannelManager.addUser(channel, userid);
                response.put("type", 1);
                response.put("request", "accept");
                channel.writeAndFlush(response.toJSONString());
                return;
            }
        }
        response.put("type", 1);
        response.put("request", "denied");
        channel.writeAndFlush(response.toJSONString());
    }


    public void handleFriendListReq(Channel channel, JSONObject message){
        JSONObject response = new JSONObject();
        String userid = message.getString("userId");
        if (!userid.isEmpty()) {
            List<FriendDO> friends = friendDOMapper.selectByUserID(userid);
            response.put("type", 2);
            JSONArray friendList = new JSONArray();
            for(FriendDO f: friends){
                String id1 = f.getUserID1();
                String id2 = f.getUserID2();
                System.out.println(userid);
                System.out.println(id1);
                System.out.println(id2);
                if(id1.equals(userid.toLowerCase())){
                    friendList.add(id2);
                }
                else{
                    friendList.add(id1);
                }
            }
            response.put("friendList", friendList);
            channel.writeAndFlush(response.toJSONString());
        }
        else{
            response.put("type", 2);
            response.put("friendList", null);
            channel.writeAndFlush(response.toJSONString());
        }
        forwardOfflineMessage(userid);
    }


    public void handleSendMsgReq(Channel channel, JSONObject message){
        String userid = message.getString("userId");
        String friendid = message.getString("friendId");
        String msg = message.getString("message");

        JSONObject forward = new JSONObject();
        forward.put("type", 4);
        forward.put("from", userid);
        forward.put("message", msg);
        nettyChannelManager.send(friendid, forward.toJSONString());
    }


    private void handleReadMsgReq(Channel channel, JSONObject message){
        String receiver = message.getString("to");;
        String sender = message.getString("from");
        JSONObject response = new JSONObject();
        response.put("type", 5);
        response.put("to", receiver);
        nettyChannelManager.send(sender, response.toJSONString());
    }


    public void handleMessage(Channel channel, String msg){
        JSONObject message = JSON.parseObject(msg);
        int type = message.getInteger("type");

        switch(type){
            case 0: // heartbeat
                logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
                handleHeartbeat(channel, message);
                break;

            case 1: // auth
                logger.info("[execute][收到连接({}) 的认证请求]", channel.id());
                handleAuthReq(channel, message);
                break;

            case 2: // friend list
                logger.info("[execute][收到连接({}) 的获取列表请求]", channel.id());
                handleFriendListReq(channel, message);
                break;

            case 3: // send message
                logger.info("[execute][收到连接({}) 的发送消息请求]", channel.id());
                handleSendMsgReq(channel, message);
                break;

            case 5:
                logger.info("[execute][收到连接({}) 的发送消息请求]", channel.id());
                handleReadMsgReq(channel, message);
                break;
            case -1: // error
                break;
        }

        // do something with msg


        // channel.writeAndFlush(some feedback) back to the sender
    }
}


