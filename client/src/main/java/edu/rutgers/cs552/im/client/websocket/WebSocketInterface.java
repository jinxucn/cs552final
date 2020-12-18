/*
 * @Author: Jin X
 * @Date: 2020-12-11 19:55:26
 * @LastEditTime: 2020-12-16 19:22:56
 */

package edu.rutgers.cs552.im.client.websocket;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.Throwable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rutgers.cs552.im.client.service.NettyClient;

@ServerEndpoint("/ws")
@Component
public class WebSocketInterface{

    private static Session _session;
    private static boolean _isInitialized = false;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static NettyClient nettyClient;

    @Autowired
    public void setNettyClient(NettyClient nettyClient){
        WebSocketInterface.nettyClient = nettyClient;
    }

    @OnOpen
    public void onOpen(Session session) throws IOException{
        if(_isInitialized)
            return;
        logger.info("WebSocket on");
        _session = session;
        _isInitialized = true;
    }

    @OnClose
    public void onClose(){
        logger.info("WebSocket close");
    }

    @OnMessage
    public void onMessage(String message) throws IOException{
        logger.info("WebSocket send to nettyClient: "+message);
        nettyClient.send(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        _session.getBasicRemote().sendText(message);
        logger.info("WebSocket send to webpage: " + message);
    }

    public void close() throws IOException {
        _session.close();
    }
}