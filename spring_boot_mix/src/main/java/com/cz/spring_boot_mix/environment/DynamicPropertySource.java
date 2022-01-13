package com.cz.spring_boot_mix.environment;
 
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DynamicPropertySource extends MapPropertySource {
 
    private static Logger                   log       = LoggerFactory.getLogger(DynamicPropertySource.class);
 
    private static ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
    static {
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                map = dynamicLoadMapInfo();
            }
 
        }, 1, 10, SECONDS);
    }
 
    public DynamicPropertySource(String name) {
        super(name, map);
    }
 
    private static Map<String, Object> map = new ConcurrentHashMap<String, Object>(64);
 
    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }
 
    //动态获取资源信息
    private static Map<String, Object> dynamicLoadMapInfo() {
        //通过http或tcp等通信协议获取配置信息
        return mockMapInfo();
    }
 
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
 
    private static Map<String, Object> mockMapInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
//        int randomData = new Random().nextInt();
//        log.info("random data{};currentTime:{}", randomData, sdf.format(new Date()));
//        map.put("dynamic-info", randomData);
        // 从本地文件中获取值
        // 不能读取项目内的,项目内的文件在jar包中无法更新,需要从外部读取
//        URL url = new URL("D:\\Users\\Desktop\\Demo.conf");
        File file = new File("/Users/sephiroth/IdeaProjects/project/spring_boot_mix/src/main/java/com/cz/spring_boot_mix/environment/Demo.conf");
        try(FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader)){
            String line = null;
            while ((line = reader.readLine()) != null){
                if(!StringUtils.isEmpty(line) && line.contains("=")){
                    String[] split = line.split("=");
                    map.put(split[0], split[1]);
                }
                line = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("解析文件出错");
        }
        display(map);
        return map;
    }

    private static void display(Map<String, Object> map){
        map.entrySet().forEach(e -> {
            System.out.println("key: " + e.getKey() + "  value: " + e.getValue());
        });
        System.out.println("---------------------");
    }

    public static void main(String[] args) {
        try{
                    SECONDS.sleep(6);}catch(Exception e){e.printStackTrace();}
        System.out.println(map.get("port"));
    }
}