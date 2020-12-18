package edu.rutgers.cs552.im.server;


import edu.rutgers.cs552.im.server.dao.UserDOMapper;
import edu.rutgers.cs552.im.server.dao.FriendDOMapper;
import edu.rutgers.cs552.im.server.dataobject.UserDO;
import edu.rutgers.cs552.im.server.dataobject.FriendDO;
import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@MapperScan(basePackages = "edu.rutgers.cs552.im.server.dao")
public class NettyServerApplication {


    // main entry, start as a spring boot app
    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

}
