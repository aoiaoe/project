package com.cz.sping_boot_milvus.milvus;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHolder {

    public static List<JSONObject> data = Arrays.asList(
            new JSONObject(Map.of("my_id", 0L, "my_vec", Arrays.asList(0.3580376395471989f, -0.6023495712049978f, 0.18414012509913835f, -0.26286205330961354f, 0.9029438446296592f), "color", "pink_8682")),
            new JSONObject(Map.of("my_id", 1L, "my_vec", Arrays.asList(0.19886812562848388f, 0.06023560599112088f, 0.6976963061752597f, 0.2614474506242501f, 0.838729485096104f), "color", "red_7025")),
            new JSONObject(Map.of("my_id", 2L, "my_vec", Arrays.asList(0.43742130801983836f, -0.5597502546264526f, 0.6457887650909682f, 0.7894058910881185f, 0.20785793220625592f), "color", "orange_6781")),
            new JSONObject(Map.of("my_id", 3L, "my_vec", Arrays.asList(0.3172005263489739f, 0.9719044792798428f, -0.36981146090600725f, -0.4860894583077995f, 0.95791889146345f), "color", "pink_9298")),
            new JSONObject(Map.of("my_id", 4L, "my_vec", Arrays.asList(0.4452349528804562f, -0.8757026943054742f, 0.8220779437047674f, 0.46406290649483184f, 0.30337481143159106f), "color", "red_4794")),
            new JSONObject(Map.of("my_id", 5L, "my_vec", Arrays.asList(0.985825131989184f, -0.8144651566660419f, 0.6299267002202009f, 0.1206906911183383f, -0.1446277761879955f), "color", "yellow_4222")),
            new JSONObject(Map.of("my_id", 6L, "my_vec", Arrays.asList(0.8371977790571115f, -0.015764369584852833f, -0.31062937026679327f, -0.562666951622192f, -0.8984947637863987f), "color", "red_9392")),
            new JSONObject(Map.of("my_id", 7L, "my_vec", Arrays.asList(-0.33445148015177995f, -0.2567135004164067f, 0.8987539745369246f, 0.9402995886420709f, 0.5378064918413052f), "color", "grey_8510")),
            new JSONObject(Map.of("my_id", 8L, "my_vec", Arrays.asList(0.39524717779832685f, 0.4000257286739164f, -0.5890507376891594f, -0.8650502298996872f, -0.6140360785406336f), "color", "white_9381")),
            new JSONObject(Map.of("my_id", 9L, "my_vec", Arrays.asList(0.5718280481994695f, 0.24070317428066512f, -0.3737913482606834f, -0.06726932177492717f, -0.6980531615588608f), "color", "purple_4976"))
    );
    public static Map map = new HashMap<Long, JSONObject>();
    static {
        for (Long i = 0L; i < 10L; i++) {
         map.put(i, data.get(i.intValue()));
        }
    }

}
