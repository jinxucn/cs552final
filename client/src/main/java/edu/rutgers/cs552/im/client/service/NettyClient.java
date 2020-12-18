package edu.rutgers.cs552.im.client.service;

import edu.rutgers.cs552.im.client.handler.NettyClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
public class NettyClient {

    private static final Integer ReconnectTimeout = 20; // for reconnect timeout

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${netty.server.host}")
    private String serverHost;
    @Value("${netty.server.port}")
    private Integer serverPort;


    @Autowired
    private NettyClientHandlerInitializer nettyClientHandlerInitializer;

    
    private EventLoopGroup eventGroup = new NioEventLoopGroup();
    private volatile Channel channel;

    /**
     * start Netty Client
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // define bootstrap, and some configures
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventGroup) 
                .channel(NioSocketChannel.class)  
                .remoteAddress(serverHost, serverPort) 
                .option(ChannelOption.SO_KEEPALIVE, true) // for alive tcp
                .option(ChannelOption.TCP_NODELAY, true)  // for small packets
                .handler(nettyClientHandlerInitializer);
        // future listener for connection status
        bootstrap.connect().addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    logger.error("[start][Netty Client connect to server at({}:{}) fail]", serverHost, serverPort);
                    reconnect();
                    return;
                }
                channel = future.channel();
                logger.info("[start][Netty Client connect to server at({}:{}) success]", serverHost, serverPort);
            }

        });
    }

    public void reconnect() {
        eventGroup.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("[reconnect][start]");
                try {
                    start();
                } catch (InterruptedException e) {
                    logger.error("[reconnect][fail]", e);
                }
            }
        }, ReconnectTimeout, TimeUnit.SECONDS);
        logger.info("[reconnect][in {} s]", ReconnectTimeout);
    }

    @PreDestroy
    public void shutdown() {
        // close the channel
        if (channel != null) {
            channel.close();
        }
        // shutdown safely
        eventGroup.shutdownGracefully();
    }


    public void send(String msg){
        if (channel == null) {
            logger.error("[send][channel is not exist]");
            return;
        }
        if (!channel.isActive()) {
            logger.error("[send][channel({})is not active]", channel.id());
            return;
        }
        // send msg to pipeline
        channel.writeAndFlush(msg);
    }
    
}
