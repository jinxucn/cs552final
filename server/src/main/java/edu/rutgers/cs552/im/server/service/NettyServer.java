package edu.rutgers.cs552.im.server.service;

import edu.rutgers.cs552.im.server.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
public class NettyServer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private NettyServerHandlerInitializer nettyServerHandlerInitializer;

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();

    private Channel channel;

    /**
     * start Netty Server
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // define bootstrap, and some configures
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker) 
                .channel(NioServerSocketChannel.class)  
                .localAddress(new InetSocketAddress(port)) 
                .option(ChannelOption.SO_BACKLOG, 1024) 
                .childOption(ChannelOption.SO_KEEPALIVE, true) // for alive tcp
                .childOption(ChannelOption.TCP_NODELAY, true)  // for small packets
                .childHandler(nettyServerHandlerInitializer);
        // future listener for connection status
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            channel = future.channel();
            logger.info("[start][Netty Server start at port{}]", port);
        }
    }

    @PreDestroy
    public void shutdown() {
        // close channel
        if (channel != null) {
            channel.close();
        }
        // shutdown safely
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

}
