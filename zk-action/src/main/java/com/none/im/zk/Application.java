package com.none.im.zk;

import com.none.im.zk.thread.RegistryZK;
import com.none.im.zk.util.ZKConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

/**
 * @Author: zl
 * @Date: 2019/5/30 22:13
 */
@SpringBootApplication
public class Application  implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ZKConfiguration zkConfiguration ;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("启动成功");
    }

    @Override
    public void run(String... strings) throws Exception {
        //获得本机IP
        String addr = InetAddress.getLocalHost().getHostAddress();
        Thread thread = new Thread(new RegistryZK(addr, zkConfiguration.getPort()));
        thread.setName("registry-zk");
        thread.start() ;
    }

}
