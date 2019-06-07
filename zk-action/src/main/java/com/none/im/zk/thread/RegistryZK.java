package com.none.im.zk.thread;

import com.none.im.zk.util.SpringBeanFactory;
import com.none.im.zk.util.ZKConfiguration;
import com.none.im.zk.util.ZKUtil;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zl
 * @Date: 2019/5/30 23:58
 */
public class RegistryZK implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RegistryZK.class);

    private ZKUtil zkUtil;

    private ZKConfiguration zkConfiguration ;

    private ZkClient zkClient;

    private String ip;
    private int port;

    public RegistryZK(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.ip = ip;
        this.port = port;
        zkUtil = SpringBeanFactory.getBean(ZKUtil.class) ;
        zkConfiguration = SpringBeanFactory.getBean(ZKConfiguration.class) ;
        zkClient = SpringBeanFactory.getBean(ZkClient.class);
    }

    @Override
    public void run() {
        //创建父节点
        zkUtil.createRootNode();

        //是否将自己注册到zk
        if (zkConfiguration.isZkSwitch()) {
            String path = zkConfiguration.getZkRoot() + "/ip-" + ip + ":" + port;
            if (!zkClient.exists(path)){
                zkUtil.createNode(path, path);
            }
            logger.info("注册 zookeeper 成功，msg=[{}]", path);
        }
        //注册监听服务
        zkUtil.subscribeEvent(zkConfiguration.getZkRoot());
    }
}
