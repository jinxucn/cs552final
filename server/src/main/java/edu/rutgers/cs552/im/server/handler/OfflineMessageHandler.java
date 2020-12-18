package edu.rutgers.cs552.im.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.rutgers.cs552.im.server.dao.OfflineDOMapper;
import edu.rutgers.cs552.im.server.dao.UserDOMapper;
import edu.rutgers.cs552.im.server.dataobject.OfflineDO;
import edu.rutgers.cs552.im.server.dataobject.UserDO;
import edu.rutgers.cs552.im.server.service.NettyChannelManager;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OfflineMessageHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private static NettyChannelManager nettyChannelManager;

    @Autowired
    private static OfflineDOMapper offlineDOMapper;

    @Autowired
    private UserDOMapper userDoMapper;

    public static void storeOfflineMessage(String user, String msg){
        JSONObject message = JSON.parseObject(msg);
        String fromId = message.getString("from");
        OfflineDO offlineDO = new OfflineDO();
        offlineDO.setFromID(fromId);
        offlineDO.setToID(user);
        offlineDO.setMessage(msg);
        offlineDO.setIs_sent(0);
        offlineDOMapper.insert(offlineDO);
    }


    public static void forwardOfflineMessage(String user){
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
}
