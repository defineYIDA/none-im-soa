package com.none.im.zk.util;

import com.alibaba.fastjson.JSON;
import com.none.im.zk.cache.ServerCache;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/5/30 23:05
 */
@Component
public class ZKUtil {

    private static Logger logger = LoggerFactory.getLogger(ZKUtil.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZKConfiguration zkConfiguration;

    @Autowired
    private ServerCache serverCache ;

    public void createRootNode() {
        boolean isExists = zkClient.exists(zkConfiguration.getZkRoot());
        if (isExists) {
            return;
        }

        //create root
        zkClient.createPersistent(zkConfiguration.getZkRoot());
    }

    public void createNode(String path, String value) {
        zkClient.createEphemeral(path, value);
    }

    public void subscribeEvent(String path) {
        //listen lochost cache
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                logger.info("清除/更新本地缓存parentPath=【{}】,currentChilds=【{}】", s, list.toString());
                //update cache
                serverCache.updateCache(list) ;
            }
        });
    }
    public List<String> getAllNode(){
        List<String> children = zkClient.getChildren("/route");
        logger.info("查询所有节点成功=【{}】", JSON.toJSONString(children));
        return children;
    }

    public void closeZK() {
        logger.info("正在关闭 ZK");
        zkClient.close();
        logger.info("关闭 ZK 成功");

    }
}
