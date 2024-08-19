package com.cz.sping_boot_milvus.milvus;

import com.alibaba.fastjson.JSONObject;
import com.cz.sping_boot_milvus.utils.VectorUtils;
import io.milvus.client.MilvusClient;
import io.milvus.grpc.DataType;
import io.milvus.param.collection.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MilvusExample {

    public static void main(String[] args) {
        MilvusClient client = ConnectToMilvus.newBuilder().withDbName("app").build();
        String collectionName = "nlp_collection";


        FieldType id = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64).withPrimaryKey(true).withAutoID(false)
                .build();
        FieldType content = FieldType.newBuilder()
                .withName("content")
                .withDataType(DataType.VarChar)
                .build();
        FieldType vec = FieldType.newBuilder()
                .withName("vec")
                .withDataType(DataType.FloatVector)
                .withDimension(768)
                .build();
        VectorUtils.createCollectionAndLoad(client, collectionName,
                List.of(id, content, vec), true);

        List<String> trainingData = new ArrayList<>();

        Random random = new Random();
        long start = random.nextLong() % 1000;
        // 预处理并插入向量数据
        List<JSONObject> vectors = new ArrayList<>();
        for (String text : trainingData) {
            JSONObject obj = new JSONObject();
            obj.put("id", start++);
            obj.put("content", text);
            obj.put("vec", VectorUtils.embedText(text));
            vectors.add(obj);
        }

        VectorUtils.insert(client, collectionName, vectors);
        
        // 搜索相似向量
        List<Float> queryVector = VectorUtils.embedText("查询文本");
        List<Long> similarIds = VectorUtils.searchSimilarVectors(client, collectionName, queryVector);
        
        // 打印结果
        for (Long vecId : similarIds) {
            System.out.println("相似的向量ID: " + vecId);
        }

        client.close();
    }
}
