package com.none.im.zk.cache;

import com.google.common.cache.LoadingCache;
import com.none.im.zk.util.ZKUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zl
 * @Date: 2019/5/30 23:22
 */
@Component
public class ServerCache {

    @Autowired
    private LoadingCache<String, String> cache;

    @Autowired
    private ZKUtil zkUtil;

    private AtomicLong index = new AtomicLong();

    public void addCache(String key) {
        cache.put(key, key);
    }

    public void updateCache(List<String> currentChilds) {
        cache.invalidateAll();
        for (String currentChild : currentChilds) {
            String key = currentChild.split("-")[1];
            addCache(key);
        }
    }

    public List<String> getAll() {

        List<String> list = new ArrayList<>();

        if (cache.size() == 0) {
            List<String> allNode = zkUtil.getAllNode();
            for (String node : allNode) {
                String key = node.split("-")[1];
                addCache(key);
            }
        }
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            list.add(entry.getKey());
        }
        return list;

    }

    public String selectServer() {
        List<String> all = getAll();
        if (all.size() == 0) {
            throw new RuntimeException("路由列表为空");
        }
        Long position = index.incrementAndGet() % all.size();
        if (position < 0) {
            position = 0L;
        }

        return all.get(position.intValue());
    }
}
