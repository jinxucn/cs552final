/*
 * @Author: Jin X
 * @Date: 2020-12-11 20:38:48
 * @LastEditTime: 2020-12-11 21:59:17
 */

package edu.rutgers.cs552.im.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping
public class SendMsgController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyClient nettyClient;

    @PostMapping("/netty")
    public String (){

        return "success";
    }

}