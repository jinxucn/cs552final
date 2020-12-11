/*
 * @Author: Jin X
 * @Date: 2020-12-11 12:04:34
 * @LastEditTime: 2020-12-11 12:07:51
 */
package edu.rutgers.cs552.im.server.config;

import edu.rutgers.cs552.im.common.dispatcher.MessageDispatcher;
import edu.rutgers.cs552.im.common.dispatcher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerConfig {

    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public MessageHandlerContainer messageHandlerContainer() {
        return new MessageHandlerContainer();
    }

}
