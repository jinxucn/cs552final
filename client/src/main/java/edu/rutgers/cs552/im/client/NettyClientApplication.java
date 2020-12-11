/*
 * @Author: Jin X
 * @Date: 2020-12-11 11:49:29
 * @LastEditTime: 2020-12-11 16:09:14
 */
package edu.rutgers.cs552.im.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {

    // @Autowired
    // private Integer serverPort;

    public static void main(String[] args) {
        // serverPort = Integer.parseInt(args[0]);
        SpringApplication.run(NettyClientApplication.class, args);
    }

}
