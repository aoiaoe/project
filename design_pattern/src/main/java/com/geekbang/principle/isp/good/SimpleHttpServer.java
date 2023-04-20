package com.geekbang.principle.isp.good;

import com.geekbang.principle.isp.good.service.Viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现一个简单的http服务器，通过访问连接返回采集的信息
 * @author jzm
 * @date 2023/4/19 : 10:24
 */
public class SimpleHttpServer {

    private Map<String, List<Viewer>> viewers = new HashMap<>();

    public SimpleHttpServer(){

    }

    public void addViewer(String url, Viewer v){
        if(!viewers.containsKey(url)){
            viewers.put(url, new ArrayList<>());
        }
        viewers.get(url).add(v);
    }

    public void run(){

    }

}
