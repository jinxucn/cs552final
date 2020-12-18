package edu.rutgers.cs552.im.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {

    // main entry of netty client, run as a spring boot app
    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
    }

}
