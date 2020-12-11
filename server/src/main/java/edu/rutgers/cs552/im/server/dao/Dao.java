/*
 * @Author: Jin X
 * @Date: 2020-12-11 22:43:25
 * @LastEditTime: 2020-12-11 23:51:52
 */

package edu.rutgers.cs552.im.server.dao;

import edu.rutgers.cs552.im.server.service.NettyChannelManager;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.channel.Channel;

@Component
public class Dao{

    @Autowired
    private NettyChannelManager nettyChannelManager;
    // provides
    // nettyChannelManager.send(user,msg);
    // nettyChannelManager.isActive(user);


    public void handleMessage(Channel channel, String msg){

        // do something with msg


        // channel.writeAndFlush(some feedback) back to the sender
    }
}