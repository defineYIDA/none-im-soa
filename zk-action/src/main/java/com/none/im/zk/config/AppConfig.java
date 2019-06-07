package com.none.im.zk.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.none.im.zk.util.ZKConfiguration;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zl
 * @Date: 2019/5/30 23:40
 */
@Configuration
public class AppConfig {

    @Autowired
    private ZKConfiguration zkConfiguration;

    @Bean
    public ZkClient buildZKClient() {
        return new ZkClient(zkConfiguration.getZkAddr(), 5000);
    }

    @Bean
    public LoadingCache<String,String> buildCache() {
        return CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return null;
            }
        });
    }
}
