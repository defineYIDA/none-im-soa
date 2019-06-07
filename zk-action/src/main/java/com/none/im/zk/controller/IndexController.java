package com.none.im.zk.controller;

import com.none.im.zk.cache.ServerCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/5/30 23:45
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ServerCache serverCache ;

    @ApiOperation("获取所有路由节点")
    @RequestMapping(value = "getAllRoute",method = RequestMethod.POST)
    @ResponseBody()
    public Object getAllRoute() {
        List<String> all = serverCache.getAll();
        return all ;
    }

    @ApiOperation("获取所有路由节点")
    @RequestMapping(value = "getOneOfRoute",method = RequestMethod.POST)
    @ResponseBody()
    public Object getOneOfRoute() {
        String server = serverCache.selectServer();
        return server ;
    }
}
