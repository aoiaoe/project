package com.cz.spring_boot_mix.beanPropertiesMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class MapperDemo {

    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name", "cz");
        jsonObject.put("age", 20);
        jsonObject.put("birth", "1995-06-21 00:00:00");
        jsonArray.add(jsonObject);
        List<MapperBean> collect = jsonArray.stream().map(r -> new PropertyMapper(r)
                .mapProperty(TestProperties.TEST)
                .unwrap(MapperBean.class)).collect(Collectors.toList());

        collect.forEach(e -> System.out.println(e));

    }
}
