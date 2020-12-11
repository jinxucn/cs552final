/*
 * @Author: Jin X
 * @Date: 2020-12-11 12:04:34
 * @LastEditTime: 2020-12-11 12:05:02
 */
package edu.rutgers.cs552.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

}
