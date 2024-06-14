package com.cz.springbootes.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jzm
 * @date 2024/6/13 : 19:54
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggTest extends Id<Long>{

    private String name;
    private Long buyCount;
    private String sex;
    private String createMonth;

    public static void main(String[] args) {
        for (AggTest aggTest : list()) {
            System.out.println(aggTest);
            System.out.println(aggTest.getId());
            System.out.println("----");
        }
    }

    public static List<AggTest> list(){
        List<AggTest> list = JSONObject.parseArray(json, AggTest.class);
        return list;
    }

    public static String json1 = "";
    
    public static String json = "[\n" +
            "      {\n" +
            "        \"id\" : \"1\",\n" +
            "          \"name\" : \"张三\",\n" +
            "          \"buyCount\" : 5,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-01\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"2\",\n" +
            "          \"name\" : \"李四\",\n" +
            "          \"buyCount\" : 5,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-01\"\n" +
            "      },\n" +
            "      {\n" +
            "\n" +
            "        \"id\" : \"3\",\n" +
            "\n" +
            "          \"name\" : \"小卷\",\n" +
            "          \"buyCount\" : 18,\n" +
            "          \"sex\" : \"女\",\n" +
            "          \"createMonth\" : \"2021-01\"\n" +
            
            "      },\n" +
            "      {\n" +
            "\n" +
            "        \"id\" : \"4\",\n" +
            "\n" +
            "          \"name\" : \"小明\",\n" +
            "          \"buyCount\" : 6,\n" +
            "          \"sex\" : \"女\",\n" +
            "          \"createMonth\" : \"2021-01\"\n" +
            
            "      },\n" +
            "      {\n" +
            "\n" +
            "        \"id\" : \"5\",\n" +
            "\n" +
            "          \"name\" : \"张三\",\n" +
            "          \"buyCount\" : 3,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-02\"\n" +
            
            "      },\n" +
            "      {\n" +
            "\n" +
            "        \"id\" : \"6\",\n" +
            "\n" +
            "          \"name\" : \"王五\",\n" +
            "          \"buyCount\" : 8,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-02\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"7\",\n" +
            "          \"name\" : \"赵四\",\n" +
            "          \"buyCount\" : 4,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-02\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"8\",\n" +
            "          \"name\" : \"诸葛亮\",\n" +
            "          \"buyCount\" : 6,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-02\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"9\",\n" +
            "          \"name\" : \"黄忠\",\n" +
            "          \"buyCount\" : 9,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-03\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"10\",\n" +
            "          \"name\" : \"李白\",\n" +
            "          \"buyCount\" : 1,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-03\"\n" +
            
            "      },{\n" +
            "        \"id\" : \"11\",\n" +
            "          \"name\" : \"赵四\",\n" +
            "          \"buyCount\" : 3,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-03\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"12\",\n" +
            "          \"name\" : \"张三\",\n" +
            "          \"buyCount\" : 2,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-03\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"13\",\n" +
            "          \"name\" : \"李四\",\n" +
            "          \"buyCount\" : 6,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-04\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"14\",\n" +
            "          \"name\" : \"王五\",\n" +
            "          \"buyCount\" : 9,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-04\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"15\",\n" +
            "          \"name\" : \"李四\",\n" +
            "          \"buyCount\" : 4,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-04\"\n" +
            
            "      },\n" +
            "      {\n" +
            "        \"id\" : \"16\",\n" +
            "          \"name\" : \"王五\",\n" +
            "          \"buyCount\" : 2,\n" +
            "          \"sex\" : \"男\",\n" +
            "          \"createMonth\" : \"2021-04\"\n" +
            
            "      }\n" +
            "    ]";
}
