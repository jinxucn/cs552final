/*
 * @Author: Jin X
 * @Date: 2020-12-11 19:53:31
 * @LastEditTime: 2020-12-11 21:53:38
 */

package edu.rutgers.cs552.im.client.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 * @author 
 */
@Configuration  
public class WebSocketConfig {  
	
    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
  
} 